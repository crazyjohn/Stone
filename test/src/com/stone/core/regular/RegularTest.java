package com.stone.core.regular;

import java.util.regex.Pattern;

public class RegularTest {
	public static final Pattern TEMPLATE_OBJECT_HEADER = Pattern
			.compile("template\\s+([^\\s]+)\\s*\\{");
	public static final Pattern TEMPLATE_OBJECT = Pattern
			.compile("template\\s+([^\\s]+)\\s*\\{\\s*\\}");

	public static void main(String[] args) {
		System.out.println("Match 'template Item {' : "
				+ TEMPLATE_OBJECT_HEADER.matcher("template Item {").matches());
		System.out.println("Match 'template Item{' : "
				+ TEMPLATE_OBJECT_HEADER.matcher("template Item{").matches());
		System.out.println("Match 'templateItem {' : "
				+ TEMPLATE_OBJECT_HEADER.matcher("templateItem {").matches());
		System.out
				.println("Match 'template ItemLevelUp {int level;// 等级 int costCoin;//消耗货币 String desc; //desc}' : "
						+ TEMPLATE_OBJECT
								.matcher(
										"template ItemLevelUp {int level;// 等级 int costCoin;//消耗货币 String desc; //desc}")
								.matches());
	}

}
