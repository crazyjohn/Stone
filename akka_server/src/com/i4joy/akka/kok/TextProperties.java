package com.i4joy.akka.kok;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;
/**
 * 
 * @author DongLei
 *	游戏的所有显示文字，从 Text.properties读取
 */
public class TextProperties {
	private static Properties text;
	public static String getText(String str)
	{
		if(text == null)
		{
			text = new Properties();
			try {
				TextProperties path = new TextProperties();
				URL url = path.getClass().getResource("/Text.txt");
//				String classPath = url.getPath();
//				String fPath = classPath.substring(1,classPath.length()-"/com/i4joy/akka/kok/TextProperties.class".length())+"\\Text.properties";
//				System.out.println("===================>"+fPath);
				text.load(new InputStreamReader(new FileInputStream(url.getPath()), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return text.getProperty(str);
	}
	
	public static void setProperty(String key,String value){
		if(text == null)
		{
			text = new Properties();
			try {
				TextProperties path = new TextProperties();
				URL url = path.getClass().getResource("/Text.txt");
//				String classPath = url.getPath();
//				String fPath = classPath.substring(1,classPath.length()-"/com/i4joy/akka/kok/TextProperties.class".length())+"\\Text.properties";
//				System.out.println("===================>"+fPath);
				text.load(new InputStreamReader(new FileInputStream(url.getPath()), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		text.setProperty(key, value);
	}
	
	public static void main(String[] args)
	{
//		getClass().getR
		TextProperties text = new TextProperties();
		URL url = text.getClass().getResource("/Text.properties");
		String path = url.getPath();
		System.out.println(path.substring(1,path.length()-"/com/i4joy/akka/kok/TextProperties.class".length()));
	}
}
