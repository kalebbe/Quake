/**
 * This is the model for Earthquakes. In the future, this will be used
 * when consuming data via API and when storing the data in the database.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-09-28
 */

package beans;

import java.sql.Timestamp;
import javax.faces.bean.ViewScoped;

@ViewScoped
public class Earthquake {
	private double magnitude;
	private Timestamp datetime;
	private double depth;
	private String location;
	private String coordinates;
	
	/**
	 * Default constructor for the Earthquake model
	 * @param magnitude of the earthquake
	 * @param dateTime that the earthquake happened
	 * @param depth of the earthquake
	 * @param location where the earthquake occurred
	 * @param coordinates Lat/Long of the earthquake
	 */
	public Earthquake(double magnitude, Timestamp datetime, double depth,
			String location, String coordinates) {
		this.magnitude = magnitude;
		this.datetime = datetime;
		this.depth = depth;
		this.location = location;
		this.coordinates = coordinates;
	}
	
	/**
	 * Getter for magnitude
	 * @return double
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Setter for magnitude
	 * @param magnitude new value to be set
	 */
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	/**
	 * Gets the date of the earthquake
	 * @return Date
	 */
	public Timestamp getDatetime() {
		return datetime;
	}

	/**
	 * Setter for the date of the earthquake
	 * @param dateTime
	 */
	public void setDatetime(Timestamp datetime) {
		this.datetime = datetime;
	}

	/**
	 * Gets the depth of the earthquake
	 * @return double
	 */
	public double getDepth() {
		return depth;
	}

	/**
	 * Sets the depth of the earthquake
	 * @param depth
	 */
	public void setDepth(double depth) {
		this.depth = depth;
	}

	/**
	 * Getter for the general location of the earthquake
	 * @return String
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Sets the general location of the earthquake
	 * @param location
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Gets the latitude and longitude of the earthquake
	 * @return String
	 */
	public String getCoordinates() {
		return coordinates;
	}

	/**
	 * Sets the lat/long of the earthquake
	 * @param coordinates
	 */
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
}
