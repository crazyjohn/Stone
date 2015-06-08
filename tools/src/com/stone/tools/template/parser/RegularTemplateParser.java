package com.stone.tools.template.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.stone.tools.template.ITemplateFileParser;
import com.stone.tools.template.ITemplateObject;
import com.stone.tools.template.TemplateObject;

/**
 * Parse the template file by regular language way;
 * 
 * @author crazyjohn
 *
 */
public class RegularTemplateParser implements ITemplateFileParser {
	/** one template */
	protected final Pattern TEMPLATE_OBJECT = Pattern
			.compile("\\s*template\\s+([^\\s]+)\\s*\\{\\s*(([^\\s]+)\\s+([^\\s]+)\\s*;\\s*(//\\s*(.*)\\s*+)?)+\\}\\s*");
	/** templates */
	public static final Pattern TEMPLATE_OBJECTS = Pattern
			.compile("(\\s*template\\s+([^\\s]+)\\s*\\{\\s*(([^\\s]+)\\s+([^\\s]+)\\s*;\\s*(//\\s*(.*)\\s*+)?)+\\}\\s*)+");

	@Override
	public List<ITemplateObject> parseFile(String filePath) throws Exception {
		List<ITemplateObject> result = new ArrayList<ITemplateObject>();
		BufferedReader reader = new BufferedReader(new FileReader(new File(
				filePath)));
		StringBuilder content = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			content.append(line + "\n");
		}
		reader.close();
		// check the file
		if (!TEMPLATE_OBJECTS.matcher(content.toString()).matches()) {
			throw new RuntimeException("Parse the file error: " + filePath
					+ ", are you kidding?");
		}
		// match
		Matcher matcher = TEMPLATE_OBJECT.matcher(content.toString());
		while (matcher.find()) {
			result.add(toTemplateObject(matcher.group().trim()));
		}
		return result;
	}

	private ITemplateObject toTemplateObject(String group) {
		return new TemplateObject(group);
	}

}
