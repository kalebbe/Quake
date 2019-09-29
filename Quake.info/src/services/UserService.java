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

import beans.User;
import data.UserDAO;

public class UserService {
	private UserDAO ud = new UserDAO();

	/**
	 * This method checks if the passwords match and then calls the DAO
	 * to register the user.
	 * @param user
	 * @return boolean
	 */
	public boolean register(User user) {
		if(!user.getPassword().equals(user.getRePass())) {
			return false;
		}
		return ud.create(user);
	}
	
	/**
	 * This method calls the DAO to log the user into the application
	 * @param user
	 * @return
	 */
	public boolean login(User user) {
		return ud.login(user);
	}
}
