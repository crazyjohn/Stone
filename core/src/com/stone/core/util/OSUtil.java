package com.stone.core.util;

import java.util.Properties;

public class OSUtil {

	/**
	 * Is app run at windows os;
	 * 
	 * @return
	 */
	public static boolean isWindowsOS() {
		Properties props = System.getProperties();
		if (props.getProperty("os.name").toLowerCase().contains("windows")) {
			return true;
		}
		return false;
	}

}
