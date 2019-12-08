/**
 * This is the controller for all things involving users. This will be
 * expanded in the future to include further error handling.
 * Added CDI and Logging with milestone 5
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-11-23
 */

package controllers;

import beans.Earthquake;
import beans.User;
import services.QuakeInterface;
import services.UserInterface;
import util.DatabaseException;
import util.LoggingInterceptor;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.interceptor.Interceptors;

@Named
@ViewScoped
@Interceptors(LoggingInterceptor.class)
public class UserController implements Serializable {
	
	private static final long serialVersionUID = -7908529345143662030L;
	
	@EJB
	private UserInterface us;
	
	@EJB
	private QuakeInterface qs;
	
	/**
	 * This method calls the business service to log the user in to the
	 * application
	 * @param user This is the user model data being passed from the view
	 * @return String
	 */
	public String login(User user) {
		try {
			/**
			 * FacesContext is now being saved to a variable because
			 * it will be used in a few places throughout the application
			 * if we get the error messaging working correctly.
			 */
			FacesContext context = FacesContext.getCurrentInstance();
			
			if(us.login(user)) {
				List<Earthquake> quakes = qs.getQuakes();
				context.getExternalContext().getRequestMap().put("quakes", quakes);
				
				/**
				 * Sets the session to show the user has logged in
				 */
				context.getExternalContext().getSessionMap().put("logged", true);
				
				/**
				 * Home.xhtml returned upon successful login
				 */
				return "Home.xhtml";
			}
			else {
				/**
				 * Login.xhtml returned upon failed login
				 */
				return "Login.xhtml";
			}
		} 
		/**
		 * 12/07/2019 added EJBTransactionRolledbackException because if the
		 * database was turned off, that exception is called rather than a 
		 * SQLException
		 */
		catch(DatabaseException | EJBTransactionRolledbackException e) {
			/**
			 * User is forwarded to the error page if the database connection has failed
			 */
			System.out.println("===================> Database connection failure");
			return "Error.xhtml";
		}
	}
	
	/**
	 * This method calls the business service to register the user
	 * @param user The model data for the user attempting to register
	 * @return String
	 */
	public String register(User user) {
		try {
			if(us.register(user)) {
				/**
				 * Successful registration
				 */
				return "Login.xhtml";
			}
			else {
				/**
				 * Failed registration
				 */
				return "Registration.xhtml";
			}
		} catch(DatabaseException | EJBTransactionRolledbackException e) {
			System.out.println("=====================> Database connection failure");
			return "Error.xhtml";
		}
	}
	
	/**
	 * This method invalidates the session and returns the user to the login
	 * page where they can login again if they choose.
	 * @return String Contains login.xhtml for navigation.
	 */
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "Login.xhtml";
	}
}
