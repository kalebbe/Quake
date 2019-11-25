/**
 * This class is used to handle all of the database operations regarding
 * earthquakes. May be expanded in the future to display more information
 * about each individual earthquake if queried.
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-10-27
 */

package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import beans.Earthquake;
import util.DatabaseException;
import util.LoggingInterceptor;

@Stateless
@Local(DataAccessInterface.class)
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class QuakeDAO implements DataAccessInterface<Earthquake>{
	
	private String url = "jdbc:mysql://localhost:3306/quake";
	private Connection conn = null;
	
	/**
	 * This method creates a new connection and returns it or a database
	 * exception.
	 * @return Connection
	 */
	public Connection getConnection() {
		try {
			Properties connProps = new Properties(); //Username and password
			connProps.put("user", "root");
			connProps.put("password", "");
			
			Connection conn = DriverManager.getConnection(url, connProps);
			return conn;
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		}
	}
	
	/**
	 * Gets and earthquake by it's specified ID. Currently not in use
	 * @param id
	 * @return Earthquake
	 */
	@Override
	public Earthquake findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method retrieves every earthquake from the database. If this were a full fledged application,
	 * this would need to be limited in some way because the amount of results would become massive
	 * very quickly (5+ earthquakes an hour).
	 * @return List<Earthquake>
	 */
	@Override
	public List<Earthquake> findAll() {
		List<Earthquake> quakes = new ArrayList<Earthquake>(); //Instantiated list of earthquakes
		try {
			conn = this.getConnection(); //Getting connection
			String sql = "SELECT * FROM earthquakes ORDER BY DATE_TIME DESC";
			PreparedStatement ps = conn.prepareStatement(sql); //Preparing sql statement for execution
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) { //Adds each quake to a list of earthquakes
				quakes.add(new Earthquake(rs.getDouble("MAGNITUDE"), rs.getTimestamp("DATE_TIME"),
						rs.getDouble("DEPTH"), rs.getString("LOCATION"), rs.getString("COORDINATES")));
			}
			
			//Closing everything
			conn.close();
			rs.close();
			ps.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		
		return quakes;
	}
	
	/**
	 * Retrieves the earthquakes in the last 24 hours. Will be updated to retrieve
	 * based upon day in future milestones
	 * @return List<Earthquake>
	 */
	public List<Earthquake> findLast24(){
		List<Earthquake> quakes = new ArrayList<Earthquake>(); //Instantiated list of earthquakes
		try {
			conn = this.getConnection(); //Getting connection
			String sql = "SELECT * FROM earthquakes WHERE DATE_TIME > DATE_SUB(NOW(), INTERVAL 1 DAY)"
					+ " ORDER BY DATE_TIME DESC";
			PreparedStatement ps = conn.prepareStatement(sql); //Preparing sql statement for execution
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) { //Adds each quake to a list of earthquakes
				quakes.add(new Earthquake(rs.getDouble("MAGNITUDE"), rs.getTimestamp("DATE_TIME"),
						rs.getDouble("DEPTH"), rs.getString("LOCATION"), rs.getString("COORDINATES")));
			}
			
			//Closing everything
			conn.close();
			rs.close();
			ps.close();
			
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		return quakes;
	}

	/**
	 * This method creates a new earthquake in the database
	 * @param t
	 * @return boolean
	 */
	@Override
	public boolean create(Earthquake t) {
		boolean result = false;
		
		try {
			conn = this.getConnection();
			String sql = "INSERT INTO earthquakes(MAGNITUDE, DATE_TIME, DEPTH, LOCATION, COORDINATES) VALUES(?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql); //Preparing for insertion
			
			ps.setDouble(1,  t.getMagnitude());
			ps.setTimestamp(2, t.getDatetime()); //Model date gets parsed into SQL date
			ps.setDouble(3, t.getDepth());
			ps.setString(4, t.getLocation());
			ps.setString(5, t.getCoordinates());
			
			ps.executeUpdate();
			
			conn.close();
			ps.close();
			
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new DatabaseException(e);
				}
			}
		}
		return result;
	}
}
