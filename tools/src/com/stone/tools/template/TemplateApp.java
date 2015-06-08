package com.stone.tools.template;

import java.util.List;

import com.stone.tools.template.parser.ITemplateFileParser;
import com.stone.tools.template.parser.RegularTemplateParser;
import com.stone.tools.template.type.ITemplateObject;

/**
 * Template app;
 * 
 * @author crazyjohn
 *
 */
public class TemplateApp {

	public static void main(String[] args) throws Exception {
		// build parser
		ITemplateFileParser parser = new RegularTemplateParser();
		// parse the file
		List<ITemplateObject> templates = parser.parseFile(System.getProperty("user.dir") + "/resources/template/Item.templ");
		System.out.println(templates);
	}

}
