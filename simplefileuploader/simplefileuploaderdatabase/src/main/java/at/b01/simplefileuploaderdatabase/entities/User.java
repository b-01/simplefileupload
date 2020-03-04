/**
 * 
 */
package at.b01.simplefileuploaderdatabase.entities;

import java.io.Serializable;

import at.b01.simplefileuploaderdatabase.util.CryptoUtil;

/**
 * @author b01
 * 
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1064133890348330731L;
	public static final int MAX_USERNAME_LENGTH = 50;
	public static final int MAX_SALT_LENGTH = 40;
	public static final int MAX_PASSWORD_LENGTH = 65;
	private String username;
	private Long id;
	private String salt;
	private String password;

	/**
	 * 
	 */
	public User() {
	}

	/**
	 * 
	 * @param username
	 */
	public User(String username, String password) throws NullPointerException {
		this.username = username;
		String[] temp = CryptoUtil.generateNewPassword(password);
		if (temp == null) {
			throw new NullPointerException("Error initializing User!");
		}
		this.password = temp[0];
		this.salt = temp[1];
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt
	 *            the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [username=").append(username).append(", id=")
				.append(id).append(", salt=").append(salt)
				.append(", password=").append(password).append("]");
		return builder.toString();
	}

	public boolean validate() {
		if ((this.salt.length() > MAX_SALT_LENGTH)
				|| (this.username.length() > MAX_USERNAME_LENGTH)
				|| (this.password.length() > MAX_PASSWORD_LENGTH)) {
			return false;
		}
		return true;
	}
}
