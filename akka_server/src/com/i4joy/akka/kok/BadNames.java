package com.i4joy.akka.kok;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class BadNames {
	private static final String path = "/文化部屏蔽字列表.txt";
	public static ArrayList<String> badNames = new ArrayList<String>();
	private static BadNames instance;

	public static BadNames getInstance() {
		if (instance == null) {
			instance = new BadNames();
			instance.init();
		}
		return instance;
	}

	public void init() {
		try {
			File file = new File(path);
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			String str;
			while ((str = br.readLine()) != null) {
				badNames.add(str.trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
