package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptedAlgorithm {

	/* Retorna un hash a partir de un tipo y un texto */
	public static String getHash(String password) {

		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(password.getBytes());
			byte[] mb = md.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < mb.length; ++i) {
				sb.append(Integer.toHexString((mb[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
