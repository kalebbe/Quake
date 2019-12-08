/**
 * This class is used to retrieve the response. Utilized in Milestone 5 to comply
 * with Factory pattern requirement.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-11-24
 */

package beans;

import java.util.List;

public class ResponseFactory {
	//Enum used to specify whether it's a response model or response data model
	public static enum ResponseType {RESPONSE_MODEL, RESPONSE_DATA_MODEL};
	
	/**
	 * Retrieves an instance of either the response model or response data model depending on what
	 * is asked for.
	 * @param type RESPONSE_MODEL OR RESPONSE_DATA_MODEL
	 * @param status Status code
	 * @param message Error/success message
	 * @param quakes The list of earthquakes
	 * @return ResponseInterface
	 */
	public static ResponseInterface getResponse(ResponseType type, int status, String message, List<Earthquake> quakes) {
		switch(type) {
			/**
			 * When enum is RESPONSE_MODEL
			 */
			case RESPONSE_MODEL:
				return new ResponseModel(status, message);
			/**
			 * When enum is RESPONSE_DATA_MODEL
			 */
			case RESPONSE_DATA_MODEL:
				return new ResponseDataModel(status, message, quakes);
			/**
			 * Ideally unreachable
			 */
			default:
				break;
		}
			return null;
	}
}
