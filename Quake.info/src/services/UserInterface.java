/**
 * This interface applies requirements for the Quake service. Used to satisfy
 * facade design pattern requirements.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */

package services;

import beans.User;

public interface UserInterface {
	
	/**
	 * Calls the DAO to register the user in the database.
	 * @param user This is the user being registered
	 * @return boolean
	 */
	public boolean register(User user);
	
	/**
	 * Calls the DAO to log the user in.
	 * @param user This is the user being logged in
	 * @return boolean
	 */
	public boolean login(User user);
}
