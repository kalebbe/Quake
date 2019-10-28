/**
 * This interface applies rules to the quake service inline with
 * the facade design pattern.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */

package services;

import java.util.List;

import beans.Earthquake;

public interface QuakeInterface {
	
	/**
	 * Defines business logic for creation of a new earthquake.
	 * @param quake
	 * @return boolean
	 */
	public boolean addQuake(Earthquake quake);
	
	/**
	 * Defines business logic for returning a list of all earthquakes.
	 * @return List<Earthquake>
	 */
	public List<Earthquake> getQuakes();
}
