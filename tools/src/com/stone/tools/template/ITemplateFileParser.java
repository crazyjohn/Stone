package com.stone.tools.template;

import java.util.List;

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
