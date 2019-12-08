/**
 * This is the data model for the Rest service response. It extends the
 * Response model and adds the list of earthquakes as the data portion of the
 * response.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */


package beans;

import java.util.List;

public class ResponseDataModel extends ResponseModel {
	
	private List<Earthquake> data;

	/**
	 * This method calls the super default constructor and attaches the data property
	 */
	public ResponseDataModel() {
		super(0, "");
		this.data = null;
	}
	
	/**
	 * Non-default constructor. Calls the super and attaches a list of earthquakes
	 * @param status The status of the response
	 * @param message The message of the response
	 * @param data The data attached to the response
	 */
	public ResponseDataModel(int status, String message, List<Earthquake> data) {
		super(status, message);
		this.data = data;
	}

	/**
	 * Returns the list of earthquakes
	 * @return List<Earthquake>
	 */
	public List<Earthquake> getData(){
		return data;
	}
	
	/**
	 * Sets the list of earthquakes
	 * @param data This is the list of earthquakes being returned in the API
	 */
	public void setData(List<Earthquake> data) {
		this.data = data;
	}
}
