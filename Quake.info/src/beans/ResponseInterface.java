/**
 * This is interface for the response model used for the rest service.
 * Implemented in Milestone 5 for the factory design pattern 
 * compliance.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-11-24
 */

package beans;

public interface ResponseInterface {
	
	/**
	 * Gets the Response status.
	 * @return int
	 */
	public int getStatus();
	
	/**
	 * Sets the response status.
	 * @param status The status of the response
	 */
	public void setStatus(int status);
	
	/**
	 * Gets the message from the Response
	 * @return String
	 */
	public String getMessage();
	
	/**
	 * Sets the message for the response.
	 * @param message The message attached to the response
	 */
	public void setMessage(String message);
}
