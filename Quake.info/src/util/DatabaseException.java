/**
 * This utility handles database exceptions that cannot be handled through
 * code. May be added to global exception handler in future milestone.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */

package util;

public class DatabaseException extends RuntimeException{
	
	private static final long serialVersionUID = -6312886868118973092L;

	/**
	 * This is just the constructor for the database exception
	 * @param e This is the exception being thrown
	 */
	public DatabaseException(Throwable e) {
		super(e);
	}
}
