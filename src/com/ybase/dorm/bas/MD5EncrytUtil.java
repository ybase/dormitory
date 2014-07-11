package com.ybase.dorm.bas;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * MD5���ܹ���<br/>
 * 
 * @DORMITORY_V1.0, yangxb, 2014��5��25��<br/>
 */
public class MD5EncrytUtil {

	private static final Logger log = Logger.getLogger(MD5EncrytUtil.class.getName());

	public static String md5Encrypt(String inStr) {

		MessageDigest md = null;
		String outStr = null;
		try {

			md = MessageDigest.getInstance("MD5"); // ����ѡ���������㷨��SHA
			byte[] digest = md.digest(inStr.getBytes());
			// ���ص���byet[]��Ҫת��ΪString�洢�ȽϷ���
			outStr = bytetoString(digest);
		} catch (NoSuchAlgorithmException nsae) {
			log.error(nsae.getMessage(), nsae);
		}
		return outStr;
	}

	public static String bytetoString(byte[] digest) {
		String str = "";
		String tempStr = "";
		for (int i = 1; i < digest.length; i++) {
			tempStr = (Integer.toHexString(digest[i] & 0xff));
			if (tempStr.length() == 1) {
				str = str + "0" + tempStr;
			} else {
				str = str + tempStr;
			}
		}

		return str.toLowerCase();
	}

}
