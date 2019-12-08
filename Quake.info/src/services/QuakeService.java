/**
 * This class handles all of the business logic for earthquake functionlity.
 * Inherits the Quake interface.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */

package services;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import beans.Earthquake;
import data.QuakeDAO;
import util.LoggingInterceptor;

@Stateless
@LocalBean
@Local(QuakeInterface.class)
@Interceptors(LoggingInterceptor.class)
public class QuakeService implements QuakeInterface {
	
	/**
	 * Utilizing interface caused a multiple class error
	 */
	@EJB
	private QuakeDAO dao;
		
	/**
	 * This method connects to the DAO to create a new earthquake
	 * in the database
	 * @param quake This is the earthquake being added to the database
	 * @return boolean
	 */
	@Override
	public boolean addQuake(Earthquake quake) {
		return dao.create(quake);
	}
	
	/**
	 * This method retrieves a list of all earthquakes from the database.
	 * @return List<Earthquake>
	 */
	@Override
	public List<Earthquake> getQuakes(){
		return dao.findAll();
	}
}
