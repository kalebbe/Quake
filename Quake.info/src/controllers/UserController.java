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
	 * @param user
	 * @return String
	 */
	public String login(User user) {
		try {
			if(us.login(user)) {
				List<Earthquake> quakes = qs.getQuakes();
				FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("quakes", quakes);
				return "Home.xhtml"; //Successful login
			}
			else {
				return "Login.xhtml"; //Failed login
			}
		} catch(DatabaseException e) {
			//Database connection failure
			System.out.println("===================> Database connection failure");
			return "Error.xhtml";
		}
	}
	
	/**
	 * This method calls the business service to register the user
	 * @param user
	 * @return String
	 */
	public String register(User user) {
		try {
			if(us.register(user)) {
				return "Login.xhtml"; //Registration successs
			}
			else {
				return "Registration.xhtml"; //Registration failure
			}
		} catch(DatabaseException e) {
			System.out.println("=====================> Database connection failure");
			return "Error.xhtml";
		}
	}
}
