/**
 * 
 */
package at.b01.simplefileuploaderdatabase.entities;

import java.io.Serializable;

/**
 * @author b01
 * 
 */
public class Storage implements Serializable {

	private static final long serialVersionUID = 9159652984569534247L;
	public static final int MAX_UID_LENGTH = 45;
	public static final int MAX_NAME_LENGTH = 260;
	private Long userid;
	private String uid;
	private String name;

	public Storage() {

	}

	/**
	 * @param id
	 * @param userid
	 * @param location
	 * @param uid
	 * @param name
	 */
	public Storage(Long userid, String uid, String name) {
		this.userid = userid;
		this.uid = uid;
		this.name = name;
	}

	/**
	 * @return the userid
	 */
	public Long getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(Long userid) {
		this.userid = userid;
	}

	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}

	/**
	 * @param uid
	 *            the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Storage [userid=" + userid + ", uid=" + uid + ", name=" + name
				+ "]";
	}

	public boolean validate() {
		if ((this.name.length() > MAX_NAME_LENGTH)
				|| (this.uid.length() > MAX_UID_LENGTH)) {
			return false;
		}
		return true;
	}
}
