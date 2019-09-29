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

import beans.User;
import services.UserService;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class UserController implements Serializable {
	UserService us = new UserService();
	
	/**
	 * This method calls the business service to log the user in to the
	 * application
	 * @param user
	 * @return String
	 */
	public String login(User user) {
		if(us.login(user)) {
			return "Home.xhtml";
		}
		else {
			return "Login.xhtml";
		}
	}
	
	/**
	 * This method calls the business service to register the user
	 * @param user
	 * @return String
	 */
	public String register(User user) {
		if(us.register(user)) {
			return "Login.xhtml";
		}
		else {
			return "Registration.xhtml";
		}
	}
}
