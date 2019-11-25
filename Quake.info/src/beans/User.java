/**
 * This is the model for Users. The password regex requires
 * passwords to have numbers, letters, and be at least 8 characters
 * 
 * 
 * @authors Kaleb Eberhart, Mick Torres
 * @version 1.0
 * @since   2019-09-28
 */

package beans;

import javax.faces.bean.ViewScoped;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ViewScoped
public class User {

	@NotNull(message="First name is a required field!")
	@Size(min=1, max=20, message="First name must be between 1 and 20 characters!")
	private String firstName;
	
	@NotNull(message="Last name is a required field!")
	@Size(min=1, max=20, message="Last name must be between 1 and 20 characters!")
	private String lastName;
	
	@NotNull(message="Email is a required field!")
	@Size(min=5, max=100, message="Email must be between 5 and 100 characters!")
	private String email;
	
	@NotNull(message="Password is a required field!")
	@Size(min=8, max=100, message="Password must be between 8 and 100 characters!")
	@Pattern(regexp = "^([0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+)[0-9a-zA-Z]*$", message="Password must contain letters and numbers!")
	private String password;
	
	@NotNull(message="Please confirm password!")
	@Size(min=8, max=100, message="Password must be between 8 and 100 characters!")
	@Pattern(regexp = "^([0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+)[0-9a-zA-Z]*$", message="Password must contain letters and numbers!")
	private String rePass;
	
	/**
	 * Default constructor for a user
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param password
	 * @param rePass
	 */
	public User(String firstName, String lastName, String email, String password, String rePass) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.rePass = rePass;
	}
	
	/**
	 * No-args constructor for a user
	 */
	public User() {
		this.firstName = "";
		this.lastName = "";
		this.email = "";
		this.password = "";
		this.rePass = "";
	}

	/**
	 * Getter for the user's first name
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for the user's first name
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the user's last name
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the user
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for the user's email
	 * @return String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This sets the email for the user
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter to retrieve the user's password
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setter to change the user's password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getter for the confirmation password
	 * @return String
	 */
	public String getRePass() {
		return rePass;
	}

	/**
	 * Sets the confirmation password
	 * @param rePass
	 */
	public void setRePass(String rePass) {
		this.rePass = rePass;
	}
}
