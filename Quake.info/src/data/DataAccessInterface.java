/**
 * This is a base interface for all of the DAO classes to follow with basic methods.
 * Updated to remove some method that were uneccesary for this project.
 * 
 * 
 * @authors  Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */

package data;

import java.util.List;

public interface DataAccessInterface <T> {
	
	/**
	 * This method gets all of a certain object from the database.
	 * @return List<T> This is the list of objects returned.
	 */
	public List<T> findAll();
	
	/**
	 * This method creates a certain object in the database.
	 * @param t This is the object being inserted.
	 * @return boolean Returns true or false dependent on database success.
	 */
	public boolean create(T t);
}
