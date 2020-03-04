/**
 * 
 */
package at.b01.simplefileuploaderdatabase.util;

/**
 * @author b01
 * 
 */
public class EncapsulatedDatabaseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6474544355348651656L;

	/**
	 * 
	 */
	public EncapsulatedDatabaseException() {
	}

	/**
	 * 
	 * @param message
	 */
	public EncapsulatedDatabaseException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param cause
	 */
	public EncapsulatedDatabaseException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 * @param message
	 * @param cause
	 */
	public EncapsulatedDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

}
