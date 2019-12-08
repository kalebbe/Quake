/**
 * This is the response model for the RESTful API used in
 * this project. This is extended by the ResponseDataModel
 * to add a list of Earthquakes.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */


package beans;

import javax.enterprise.inject.Produces;

public class ResponseModel implements ResponseInterface{
	private int status;
	private String message;
	
	/**
	 * Non-default constructor of the response model. Creates a new ResponseModel
	 * object with the status and message as required parameters.
	 * @param status Status is an error code returned based on success/failure
	 * @param message The message tells the user what went wrong
	 */
	public ResponseModel(int status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * Getter for the response model's status.
	 * @return int
	 */
	@Produces
	public int getStatus() {
		return status;
	}

	/**
	 * Setter for the response model's status.
	 * @param status The error code returned
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Getter for the response model's message.
	 * @return String
	 */
	@Produces
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for the response model's message.
	 * @param message The message returned
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}
