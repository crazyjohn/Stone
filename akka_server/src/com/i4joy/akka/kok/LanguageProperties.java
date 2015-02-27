/**
 * 
 */
package com.i4joy.akka.kok;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;

/**
 * @author Administrator
 *
 */
public class LanguageProperties {
	
	private static Properties text;

	/**
	 * 
	 */
	public LanguageProperties() {
		
	}
	
	public static String getText(String str){
		if(text == null){
			text = new Properties();
			try {
				TextProperties path = new TextProperties();
				URL url = path.getClass().getResource("/Language.txt");
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

}
