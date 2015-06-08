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
import com.stone.tools.template.field.ITemplateObjectField;
import com.stone.tools.template.field.TemplateObjectField;

/**
 * Parse the template file by regular language way;
 * 
 * @author crazyjohn
 *
 */
public class RegularTemplateParser implements ITemplateFileParser {
	/** the header */
	protected final Pattern TEMPLATE_OBJECT_HEADER = Pattern.compile("\\s*template\\s+([^\\s]+)\\s*\\{");
	/** field */
	protected final Pattern TEMPLATE_OBJECT_FIELD = Pattern.compile("\\s*([^\\s]+)\\s+([^\\s]+)\\s*;\\s*(//\\s*(.*)\\s*+)?");
	/** one template */
	protected final Pattern TEMPLATE_OBJECT = Pattern
			.compile("\\s*template\\s+([^\\s]+)\\s*\\{\\s*(([^\\s]+)\\s+([^\\s]+)\\s*;\\s*(//\\s*(.*)\\s*+)?)+\\}\\s*");
	/** templates */
	public static final Pattern TEMPLATE_OBJECTS = Pattern
			.compile("(\\s*template\\s+([^\\s]+)\\s*\\{\\s*(([^\\s]+)\\s+([^\\s]+)\\s*;\\s*(//\\s*(.*)\\s*+)?)+\\}\\s*)+");

	@Override
	public List<ITemplateObject> parseFile(String filePath) throws Exception {
		List<ITemplateObject> result = new ArrayList<ITemplateObject>();
		BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
		StringBuilder content = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			content.append(line + "\n");
		}
		reader.close();
		// check the file
		if (!TEMPLATE_OBJECTS.matcher(content.toString()).matches()) {
			throw new RuntimeException("Parse the file error: " + filePath + ", are you kidding?");
		}
		// match
		Matcher matcher = TEMPLATE_OBJECT.matcher(content.toString());
		while (matcher.find()) {
			result.add(toTemplateObject(matcher.group().trim()));
		}
		return result;
	}

	/**
	 * Converte the template file to template object;
	 * 
	 * @param group
	 * @return
	 */
	private ITemplateObject toTemplateObject(String group) {
		// template name
		String templateName = parseTemplateName(group);
		// template fields
		List<ITemplateObjectField> fields = parseTemplateFields(group);
		return new TemplateObject(group, templateName, fields);
	}

	private ITemplateObjectField toTemplateField(String group) {
		String[] content = group.split("//");
		String comment = content[1];
		String[] fieldInfo = content[0].split(" ");
		String fieldType = fieldInfo[0];
		String fieldName = fieldInfo[1].substring(0, fieldInfo[1].length() - 1);
		return new TemplateObjectField(fieldType, fieldName, comment);
	}

	/**
	 * Parse the template's fields;
	 * 
	 * @param group
	 * @return
	 */
	private List<ITemplateObjectField> parseTemplateFields(String group) {
		// get the fileds
		Matcher matcher = TEMPLATE_OBJECT_FIELD.matcher(group);
		List<ITemplateObjectField> result = new ArrayList<ITemplateObjectField>();
		while (matcher.find()) {
			result.add(toTemplateField(matcher.group().trim()));
		}
		return result;
	}

	/**
	 * Parse the template's name;
	 * 
	 * @param group
	 * @return
	 */
	private String parseTemplateName(String group) {
		Matcher matcher = TEMPLATE_OBJECT_HEADER.matcher(group);
		matcher.find();
		String header = matcher.group().trim();
		return header.split(" ")[1] + "Template";
	}

}
