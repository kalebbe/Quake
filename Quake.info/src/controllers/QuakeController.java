/**
 * This is the controller for all things involving earthquakes. Updated
 * with milestone 5 to implement CDI and the logging interceptor.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-11-23
 */


package controllers;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import beans.Earthquake;
import services.QuakeInterface;
import util.DatabaseException;
import util.LoggingInterceptor;

@Named
@ViewScoped
@Interceptors(LoggingInterceptor.class)
public class QuakeController implements Serializable {
	
	private static final long serialVersionUID = 612727005473746443L;
	
	@EJB
	private QuakeInterface qs;
	
	/**
	 * Takes the user back to the home page with the tabular report of earthquakes.
	 * @return String
	 */
	public String goHome() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			
			List<Earthquake> quakes = qs.getQuakes();
			context.getExternalContext().getRequestMap().put("quakes", quakes);
			return "Home.xhtml";
		} 
		/**
		 * Added EJBTransactionRolledbackException for database access attempts when
		 * the database is shut down.
		 */
		catch(DatabaseException | EJBTransactionRolledbackException e) {
			System.out.println("======================> Database connection failure!");
			return "Error.xhtml";
		}
	}
	
	/**
	 * Sends the user to the chart page with a line graph of earthquake magnitudes.
	 * @return String
	 */
	public String viewChart() {
		try {
			return "Chart.xhtml";
		} catch(DatabaseException | EJBTransactionRolledbackException e) {
			System.out.println("======================> Database connection failure!");
			return "Error.xhtml";
		}
	}
}
