package com.stone.core.regular;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regular test;
 * 
 * @author crazyjohn
 *
 */
public class RegularTest {
	/** header */
	public static final Pattern TEMPLATE_OBJECT_HEADER = Pattern
			.compile("template\\s+([^\\s]+)\\s*\\{");
	/** whole body */
	public static final Pattern TEMPLATE_OBJECT = Pattern
			.compile("\\s*template\\s+([^\\s]+)\\s*\\{\\s*(([^\\s]+)\\s+([^\\s]+)\\s*;\\s*(//\\s*(.*)\\s*+)?)+\\}\\s*");
	/** templates */
	public static final Pattern TEMPLATE_OBJECTS = Pattern
			.compile("\\s*template\\s+([^\\s]+)\\s*\\{\\s*(([^\\s]+)\\s+([^\\s]+)\\s*;\\s*(//\\s*(.*)\\s*+)?)+\\}\\s*");

	public static void main(String[] args) throws IOException {
		// head
		System.out.println("Match 'template Item {' : "
				+ TEMPLATE_OBJECT_HEADER.matcher("template Item {").matches());
		// body
		String targetString = "template ItemLevelUp { \n"
				+ "int level; // level \n" + "int costCoin;// costCoin \n"
				+ "String desc;\n" + "} ";
		System.out.println("Match --'\n" + targetString + "\n' : "
				+ TEMPLATE_OBJECT.matcher(targetString).matches());
		// read from file
		FileReader in = new FileReader(new File(System.getProperty("user.dir")
				+ "/resources/template/Item.templ"));
		BufferedReader reader = new BufferedReader(in);
		StringBuilder content = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			content.append(line + "\n");
		}
		// print content
		System.out.println(content.toString());
		// match
		Matcher matcher = TEMPLATE_OBJECTS.matcher(content.toString());
		
		System.out.println(matcher.find());
		System.out.println(matcher.group());
		reader.close();

	}

}
