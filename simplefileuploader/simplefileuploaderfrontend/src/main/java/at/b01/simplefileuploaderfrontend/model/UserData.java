package at.b01.simplefileuploaderfrontend.model;

public class UserData {

	private Long userId;
	private String username;
	private Long files;

	public UserData(Long userId, String username, Long files) {
		this.userId = userId;
		this.username = username;
		this.files = files;
	}

	/**
	 * @return the userId
	 */
	public final Long getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public final void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the username
	 */
	public final String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public final void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the files
	 */
	public final Long getFiles() {
		return files;
	}

	/**
	 * @param files
	 *            the files to set
	 */
	public final void setFiles(Long files) {
		this.files = files;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserData [username=" + username + ", files=" + files + "]";
	}
}
