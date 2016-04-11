package com.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * <p>
 * ���ܹ�����
 * </p>
 * 
 * @author ��½���xudejian_dev@126.com��QQ:66018777��
 * 
 */
public class CryptUtils {
	
	/**
	 * <p>
	 * �������_
	 * </p>
	 */
	private static final String DES_CRYPT_KEY = "XuAndLuo";

	/**
	 * <p>
	 * ���ܷ�ʽ
	 * </p>
	 */
	private static final String ALGORITHM = "DES";

	/**
	 * Ĭ������
	 */
	private static final String PASSWORD_DEFAULT = "88888888";

	// /**
	// * <p>
	// * ���ܣ���passwordΪ����Ĭ��{@link #PASSWORD_DEFAULT}
	// * </p>
	// *
	// * @param password
	// * @return String
	// */
	// public static String cryptPswd(String password) {
	// if (password == null || password.isEmpty()) {
	// password = PASSWORD_DEFAULT;
	// }
	// try {
	// SecretKey deskey = new SecretKeySpec(DES_CRYPT_KEY.getBytes(),
	// ALGORITHM);
	// Cipher cipher = Cipher.getInstance(ALGORITHM);
	// cipher.init(Cipher.ENCRYPT_MODE, deskey, new
	// java.security.SecureRandom());
	// byte[] encPswd = cipher.doFinal(password.getBytes());
	// return new String(Base64.encode(encPswd));
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// return password;
	// }
	//
	// /**
	// * <p>
	// * ���ܣ���Ϊ���򷵻�Ĭ������{@link #PASSWORD_DEFAULT}
	// * </p>
	// *
	// * @param password
	// * @return String
	// */
	// public static String decryptPswd(String password) {
	// if (password == null || password.isEmpty()) {
	// password = PASSWORD_DEFAULT;
	// }
	// try {
	// SecretKey deskey = new SecretKeySpec(DES_CRYPT_KEY.getBytes(),
	// ALGORITHM);
	// Cipher cipher = Cipher.getInstance(ALGORITHM);
	// cipher.init(Cipher.DECRYPT_MODE, deskey);
	// byte[] encPswd = cipher.doFinal(Base64.decode(password.toCharArray()));
	// return new String(encPswd).trim();
	// } catch (Exception ex) {
	// }
	// return password;
	// }

	/**
	 * 
	 * <p>
	 * ���������Ƿ�ƥ��
	 * </p>
	 * <p>
	 * author ��½���Email��xudejian_dev@126.com��QQ��66018777��
	 * </p>
	 * 
	 * @param source
	 *            ����
	 * @param sign
	 *            ����
	 * @return boolean
	 */
	public static boolean match(String source, String sign) {
		if (source == null || sign == null) {
			return false;
		}
		return source.equals(decode(sign));
	}

	/**
	 * <p>
	 * ������ܣ���passwordΪ����Ĭ��{@link #PASSWORD_DEFAULT}
	 * </p>
	 * 
	 * @param password
	 * @return String
	 */
	public static String encode(String password) {
		if (password == null || password.isEmpty()) {
			password = PASSWORD_DEFAULT;
		}
		try {
			byte[] key = DES_CRYPT_KEY.getBytes();
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
			byte[] encPswd = cipher.doFinal(password.getBytes());
			return StringUtils.bytesToHex(encPswd);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return password;
	}

	/**
	 * <p>
	 * �������
	 * </p>
	 * <p>
	 * author ��½���xudejian_dev@126.com��QQ:66018777��
	 * </p>
	 * 
	 * @param password
	 * @return String
	 */
	public static String decode(String password) {
		if (password == null || password.isEmpty()) {
			password = PASSWORD_DEFAULT;
		}
		try {
			byte[] key = DES_CRYPT_KEY.getBytes();
			byte[] bytesrc = StringUtils.hexToBytes(password);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			DESKeySpec desKeySpec = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance(ALGORITHM);
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(key);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
			byte[] retByte = cipher.doFinal(bytesrc);
			return new String(retByte).trim();
		} catch (Exception ex) {
		}
		return password;
	}

	public static void main(String[] args) {
		System.out.println(encode("123"));
	}

}
