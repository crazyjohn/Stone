package com.i4joy.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Encrypt {
	private static Encrypt instance;

	private static Encrypt getInstance() {
		if (instance == null) {
			instance = new Encrypt();
		}
		return instance;
	}

	protected final Log logger = LogFactory.getLog(getClass());

	public static byte[] getEncryptkey() {
		byte[] encryptkey = new byte[11];
		for (int i = 0; i < encryptkey.length - 1; i++) {
			encryptkey[i] = (byte) Tools.getRandomNum(0, 100);
		}
		encryptkey[10] = (byte) Tools.getRandomNum(100, 120);
		return encryptkey;
	}
	
	public static String getEncryptkeyStr() {
		String encryptkey = "";
		for (int i = 0; i < 10; i++) {
			encryptkey += byteToString(Tools.getRandomNum(0, 100));
		}
		encryptkey += byteToString(Tools.getRandomNum(100, 120));
		return encryptkey;
	}
	
	public static String byteToString(int value)
	{
		String str = ""+value;
		if(value < 100)
		{
			str = "0"+str;
		}
		if(value < 10)
		{
			str = "0"+str;
		}
		return str;
	}

	public static String dencrypt(String s, byte[] encryptkey) {
		
//		for(int i = 0; i < encryptkey.length; i++)
//		{
//			System.out.println("dencrypt "+encryptkey[i]);
//		}
		byte[] data = null;
		try {
			data = s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Tools.printError(e, getInstance().logger, null);
		}
		int len = data.length;
		int keylen = encryptkey.length;
		byte key;
		for (int i = 0; i < len; i++) {
			key = i < keylen ? encryptkey[i] : encryptkey[0];
			data[i] = (byte) (data[i] ^ key);
		}
		try {
			return new String(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Tools.printError(e, getInstance().logger, null);
		}
		return "";
	}
}
