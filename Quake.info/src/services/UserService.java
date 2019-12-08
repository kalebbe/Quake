/**
 * This class handles all of the business logic for users functionality.
 * In the future, this will likely hash passwords and perform more complex
 * functions
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-09-28
 */

package services;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import beans.User;
import data.UserDAO;
import util.LoggingInterceptor;

@Stateless
@Local(UserInterface.class)
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class UserService implements UserInterface {
	
	/**
	 * Class used in lieu of interface bc method is not in interface
	 */
	@EJB
	private UserDAO ud;

	/**
	 * This method checks if the passwords match and then calls the DAO
	 * to register the user.
	 * @param user This is the user being registered
	 * @return boolean
	 */
	@Override
	public boolean register(User user) {
		if(!user.getPassword().equals(user.getRePass())) {
			return false;
		}
		/**
		 * This checks to ensure the email is not taken before
		 * registering the user in the database
		 */
		if(ud.checkEmail(user.getEmail())) {
			return false;
		}
		return ud.create(user);
	}
	
	/**
	 * This method calls the DAO to log the user into the application
	 * @param user This is the user attempting to log in
	 * @return boolean
	 */
	@Override
	public boolean login(User user) {
		return ud.login(user);
	}
}
