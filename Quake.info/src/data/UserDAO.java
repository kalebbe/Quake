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
import beans.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO implements DataAccessInterface<User> {
	//Url of the database
	private String url = "jdbc:mysql://localhost:3306/quake";
	
	/**
	 * This method generates a database connection for the other methods.
	 * @return Connection
	 */
	public Connection getConnection() {
		try {
			//Sets properties for the connection
			Properties connProps = new Properties();
			connProps.put("user", "root");
			connProps.put("password", "");
			
			Connection conn = DriverManager.getConnection(url, connProps);
			return conn;
		} catch (Exception e) {
			//Failled connection with a null return
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This method is used to find users by their id. Currently not in use
	 * @param id
	 * @return User
	 */
	@Override
	public User findById(int id) {
		return null;
	}

	/**
	 * This method retrieves all users from the database. Will not be used in this project
	 * @return List<User>
	 */
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method registers a new user in the database. In future versions, this will
	 * check for an existing email beforehand.
	 * @param t
	 * @return boolean
	 */
	@Override
	public boolean create(User t) {
		try {
			Connection conn = this.getConnection();
			String sql = "INSERT INTO users (FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
		
			//Setting the parameters for the query
			ps.setString(1, t.getFirstName());
			ps.setString(2, t.getLastName());
			ps.setString(3, t.getEmail());
			ps.setString(4, t.getPassword());
			
			ps.executeUpdate();
			conn.close();
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This method will be used to update users in the database. May not be used in this
	 * project
	 * @param t
	 * @return boolean
	 */
	@Override
	public boolean update(User t) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * This method would be used to delete a specific user from the database
	 * @param id
	 * @return boolean
	 */
	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * This method logs the user in to the website if their email and password match
	 * @param t
	 * @return
	 */
	public boolean login(User t) {
		try {
			Connection conn = this.getConnection();
			String sql = "SELECT COUNT(*) FROM users WHERE EMAIL=? AND PASSWORD=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, t.getEmail());
			ps.setString(2, t.getPassword());
			
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next()) {
				int count = resultSet.getInt(1);
				
				conn.close();
				
				if(count > 0) {
					return true;
				}
				else {
					return false;
				}
			}
			
			conn.close();
			return false;
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
