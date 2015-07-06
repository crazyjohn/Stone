package com.stone.tools.template.type;

import java.util.List;

import com.stone.tools.template.field.ITemplateObjectField;

/**
 * The template object;
 * 
 * @author crazyjohn
 *
 */
public interface ITemplateObject {
	/**
	 * Get all fields;
	 * 
	 * @return
	 */
	public List<ITemplateObjectField> getAllFileds();

	/**
	 * Get the templateName;
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Get the template class comment;
	 * 
	 * @return
	 */
	public String getComment();
}
