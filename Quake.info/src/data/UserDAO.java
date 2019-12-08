/**
 * This class is used to handle all of the database operations regarding
 * users.
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-09-28
 */
package data;

import java.util.List;
import java.util.Properties;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import beans.User;
import util.DatabaseException;
import util.LoggingInterceptor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Stateless
@Local(DataAccessInterface.class)
@LocalBean
@Interceptors(LoggingInterceptor.class)
public class UserDAO implements DataAccessInterface<User> {

	/**
	 * The url of the database
	 */
	private String url = "jdbc:mysql://localhost:3306/quake";
	private Connection conn = null;
	
	/**
	 * This method generates a database connection for the other methods.
	 * @return Connection
	 */
	public Connection getConnection() {
		try {
			/**
			 * Sets properties for the connection
			 */
			Properties connProps = new Properties();
			connProps.put("user", "root");
			connProps.put("password", "");
			
			Connection conn = DriverManager.getConnection(url, connProps);
			return conn;
		} catch (SQLException e ) {
			/**
			 * Failled connection with a null return
			 */
			e.printStackTrace();
			throw new DatabaseException(e);
		}
	}

	/**
	 * This method retrieves all users from the database. Will not be used in this project
	 * @return List<User>
	 */
	@Override
	public List<User> findAll() {
		return null;
	}

	/**
	 * This method registers a new user in the database. In future versions, this will
	 * check for an existing email beforehand.
	 * @param t This is the user object being created in the database
	 * @return boolean
	 */
	@Override
	public boolean create(User t) {
		boolean result = false;
		try {
			conn = this.getConnection();
			
			/**
			 * MySQL statement used to insert the user into the database
			 */
			String sql = "INSERT INTO users (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
		
			/**
			 * Setting the parameters for the query
			 */
			ps.setString(1, t.getFirstName());
			ps.setString(2, t.getLastName());
			ps.setString(3, t.getEmail());
			ps.setString(4, t.getPassword());
			
			ps.executeUpdate();
			
			/**
			 * Closing the preparedstatement and conneciton
			 */
			ps.close();
			conn.close();
			
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		} 
		/**
		 * Closes connection if it is not null
		 */
		finally {
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
	
	/**
	 * This methods checks the database to see if the user's email is already in use.
	 * @param email This is the email being checked
	 * @return boolean
	 */
	public boolean checkEmail(String email) {
		boolean result = false;
		try {
			/**
			 * Retrieving a database connection and creating the SQL statement for count
			 */
			conn = this.getConnection();
			String sql = "SELECT count(*) FROM users WHERE EMAIL=?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			
			ResultSet resultSet = ps.executeQuery();
			
			/**
			 * This loop goes through the results from the query then
			 * checks if the result count is 1 or higher. This would
			 * imply that the email already exists in the database
			 */
			while(resultSet.next()) {
				int count = resultSet.getInt(1);
				
				if(count > 0) {
					result = true;
				}
			}
			
			conn.close(); ps.close(); resultSet.close();
			
		} 
		/**
		 * Catching sql exceptions
		 */
		catch(SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		} finally {
			if(conn!= null) {
				try {
					conn.close();
				} catch(SQLException e) {
					throw new DatabaseException(e);
				}
			}
		}
		return result;
	}
	
	/**
	 * This method logs the user in to the website if their email and password match
	 * @param t This is the user being logged in
	 * @return
	 */
	public boolean login(User t) {
		boolean result = false;
		
		try {
			conn = this.getConnection();
			String sql = "SELECT COUNT(*) FROM users WHERE EMAIL=? AND PASSWORD=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, t.getEmail());
			ps.setString(2, t.getPassword());
			
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				/**
				 * Gets the number of returns
				 */
				int count = resultSet.getInt(1);
						
				/**
				 * Count will be higher than 0 if the email and password exist in the database
				 */
				if(count > 0) {
					result = true;
				}
			}
			
			resultSet.close(); ps.close(); conn.close();
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new DatabaseException(e);
		} 
		/**
		 * Final check to close the connection if it is not null
		 */
		finally {
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
