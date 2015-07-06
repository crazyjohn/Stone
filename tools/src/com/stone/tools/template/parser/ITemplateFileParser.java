package com.stone.tools.template.parser;

import java.util.List;

import com.stone.tools.template.type.ITemplateObject;

/**
 * The template file parser;
 * 
 * @author crazyjohn
 *
 */
public interface ITemplateFileParser {

	/**
	 * Parse the template file to TemplateObject list;
	 * 
	 * @param filePath
	 *            The template file's path;
	 * @return
	 * @throws Exception
	 */
	public List<ITemplateObject> parseFile(String filePath) throws Exception;
}
