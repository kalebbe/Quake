/**
 * This is a base interface for all of the DAO classes to follow with basic methods.
 * 
 * 
 * @authors  Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-09-28
 */

package data;

import java.util.List;

public interface DataAccessInterface <T> {
	/**
	 * This method will find any object by its ID in the database.
	 * @param id This is used to get the Object.
	 * @return T This is the object returned.
	 */
	public T findById(int id);
	
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
	
	/**
	 * This method updates a certain object in the database.
	 * @param t This is the object being updated.
	 * @return boolean Returns true or false dependent on database success.
	 */
	public boolean update(T t);
	
	/**
	 * This method deletes a certain object in the database.
	 * @param id This is the id of the object being deleted.
	 * @return boolean Returns true or false dependent on database success.
	 */
	public boolean delete(int id);
}
