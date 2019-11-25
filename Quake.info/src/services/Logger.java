/**
 * This is the singleton logger used to hold all of the method entry/exits that are
 * captured by the interceptor. This class does not have @interceptor because it would
 * cause an infinite loop.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-11-23
 */

package services;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Startup
@Singleton
public class Logger {
	private static List<String> log;
	
	/**
	 * This method creates the logger when the program
	 * is first started and prevents multiple logs from
	 * being created.
	 */
	@PostConstruct
	public void init() {
		log = new ArrayList<String>();
	}
	
	/**
	 * Returns the instance of the log
	 * @return List<String>
	 */
	public static List<String> getLog() {
		return log;
	}
	
	/**
	 * Places the input string into the logger.
	 * @param input
	 */
	public void putLog(String input) {
		//Adds the input to the logger
		log.add(input);
	}
}
