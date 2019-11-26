/**
 * This is the rest service for earthquakes in our project. We decided to include a GET
 * method for retrieving all earthquakes in case users would want to utilize that
 * for their own benefit.
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */

package services;


import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import beans.Earthquake;
import beans.ResponseFactory;
import beans.ResponseFactory.ResponseType;
import beans.ResponseInterface;
import util.LoggingInterceptor;


@RequestScoped
@Path("/quakes")
@Produces({ "application/json" })
@Consumes({ "application/json" })
@Interceptors(LoggingInterceptor.class)
public class QuakeRestService {
	
	@EJB
	private QuakeInterface service;
	
	/**
	 * This method parses retrieved JSON into java objects and calls the quake business
	 * service to put them into the database.
	 * @param key Used to authenticate user pushing POST.
	 * @param msg This is the actual JSON being pushed.
	 * @return ResponseInterface
	 * @throws ParseException
	 */
	@POST
	@Path("/postdata/{key}")
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseInterface addQuakes(@PathParam("key") String key, String msg) throws ParseException {
		//Ghetto authentification. May be updated to tokken authentification in future milestone
		//dependent upon time restraints.
		if(!key.equals("QuakeKey530")) { 
			return ResponseFactory.getResponse(ResponseType.RESPONSE_MODEL, -2, "Authentification Error", null);
		}
		else {
			try {
				JSONArray jArray = new JSONArray(msg); //Creates an array of JSON objects from the data pushed
				
				for(int i = 0; i < jArray.length(); i++) {
					JSONObject jObj = jArray.getJSONObject(i); //Instantiating each JSON object
										
					//Parsing the date time into a java date time and removing the 't' in the datetime
					Timestamp dateTime = Timestamp.valueOf(jObj.getString("datetime").replace('t', ' '));
					
					//Creating a new Earthquake in the database for each JSON object
					service.addQuake(new Earthquake(jObj.getDouble("magnitude"), dateTime,
							jObj.getDouble("depth"), jObj.getString("location"),
							jObj.getString("coordinates")));
				}
				
				return ResponseFactory.getResponse(ResponseType.RESPONSE_MODEL, 0, 
						"Successfully Posted to Server", null);
				
			} catch(JSONException e) { //Catching JSON exceptions
				e.printStackTrace();
				return ResponseFactory.getResponse(ResponseType.RESPONSE_MODEL, -1, 
						"System Exception", null);
			}
		}
	}
	
	/**
	 * This method utilized HTTP GET to return a list of all the earthquakes
	 * in the database.
	 * @return ResponseDataModel
	 */
	@GET
	@Path("/getquakes")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseInterface getQuakes() {
		ResponseInterface response;
		List<Earthquake> quakes = new ArrayList<Earthquake>();
		try {
			quakes = service.getQuakes();
			response = ResponseFactory.getResponse(ResponseType.RESPONSE_DATA_MODEL,
					0, "Success", quakes); //Successfully retrieved earthquakes.
		} catch(Exception e) {
			response = ResponseFactory.getResponse(ResponseType.RESPONSE_MODEL,
					-1, "System Exception", null);
		}
		return response;
	}
}
