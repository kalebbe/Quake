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

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import beans.User;
import data.UserDAO;

@Stateless
@Local(UserInterface.class)
@LocalBean
public class UserService implements UserInterface {
	private UserDAO ud = new UserDAO();

	/**
	 * This method checks if the passwords match and then calls the DAO
	 * to register the user.
	 * @param user
	 * @return boolean
	 */
	@Override
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
	@Override
	public boolean login(User user) {
		return ud.login(user);
	}
}
