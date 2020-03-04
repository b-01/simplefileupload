package at.b01.simplefileuploaderdatabase.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class CryptoUtil {

	private static final int SHA1 = 0;
	private static final int SHA256 = 1;
	private static final int SHA1_LENGTH = 40;
	private static final int SHA256_LENGTH = 64;

	public static String[] generateNewPassword(String plainPassword) {
		return generateNewPassword(plainPassword, SHA256);
	}

	public static String generatePassword(String plainPassword, String salt) {
		return generatePassword(plainPassword, salt, SHA256);
	}

	public static String generateStorageUid(String filename) {
		return generateNewPassword(filename, SHA1)[0];
	}

	public static boolean isValidStorageUid(String uid) {
		return (uid.matches("[0-9a-fA-F]+") && (uid.length() == SHA1_LENGTH || uid
				.length() == SHA256_LENGTH));
	}

	/**
	 * 
	 * @param plainPassword
	 * @return Index 0 = Password <br/>
	 *         Index 1 = Salt <br/>
	 *         or null if an error occurred
	 */
	private static String[] generateNewPassword(String plainPassword,
			int hashfunction) {
		// Returnvalue: Index 0 = PasswordHash, 1 = Salt
		String[] returnValue = new String[2];

		returnValue[1] = UUID.randomUUID().toString().replace("-", "")
				.substring(0, 32);
		returnValue[0] = generatePassword(plainPassword, returnValue[1],
				hashfunction);

		if (returnValue[0] == null) {
			return null;
		}

		return returnValue;
	}

	/**
	 * 
	 * @param plainPassword
	 * @param salt
	 * @return
	 */
	private static String generatePassword(String plainPassword, String salt,
			int hashfunction) {
		switch (hashfunction) {
		case SHA1:
			return sha1(plainPassword + "_" + salt);
		case SHA256:
			return sha256(plainPassword + "_" + salt);
		default:
			return sha256(plainPassword + "_" + salt);
		}

	}

	private static String sha256(String plain) {
		byte byteData[];
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(plain.getBytes());
			byteData = md.digest();
		} catch (NoSuchAlgorithmException ex) {
			return null;
		}

		// Convert the byte to hex format
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100,
					16).substring(1));
		}

		return stringBuffer.toString();
	}

	private static String sha1(String plain) {
		byte byteData[];
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(plain.getBytes());
			byteData = md.digest();
		} catch (NoSuchAlgorithmException ex) {
			return null;
		}

		// Convert the byte to hex format
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			stringBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100,
					16).substring(1));
		}

		return stringBuffer.toString();
	}

}