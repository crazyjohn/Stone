package com.stone.tools.template.app;

import java.util.List;

import com.stone.tools.template.ITemplateFileParser;
import com.stone.tools.template.ITemplateObject;
import com.stone.tools.template.parser.RegularTemplateParser;

public class TemplateApp {

	public static void main(String[] args) throws Exception {
		ITemplateFileParser parser = new RegularTemplateParser();
		List<ITemplateObject> templates = parser.parseFile(System.getProperty("user.dir")
				+ "/resources/template/Item.templ");
		System.out.println(templates);
	}

}
