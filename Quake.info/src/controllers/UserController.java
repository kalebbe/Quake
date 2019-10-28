/**
 * This is the controller for all things involving users. This will be
 * expanded in the future to include further error handling.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-09-28
 */

package controllers;

import beans.Earthquake;
import beans.User;
import services.QuakeService;
import services.UserService;
import util.DatabaseException;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

@ManagedBean
@ViewScoped
public class UserController implements Serializable {
	
	private static final long serialVersionUID = -7908529345143662030L;
	
	UserService us = new UserService();
	QuakeService qs = new QuakeService();
	
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
