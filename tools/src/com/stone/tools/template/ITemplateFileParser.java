package com.stone.tools.template;

import java.util.List;

public interface ITemplateFileParser {
	public List<ITemplateObject> parseFile(String filePath) throws Exception;
}
