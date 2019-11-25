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

public class ResponseFactory {
	public static enum ResponseType {RESPONSE_MODEL};
	
	public static ResponseInterface getResponse(ResponseType type, int status, String message) {
		switch(type) {
			case RESPONSE_MODEL:
				return new ResponseModel(status, message);
			default:
				break;
		}
			return null;
	}
}
