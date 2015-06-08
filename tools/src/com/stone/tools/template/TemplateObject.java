package com.stone.tools.template;

import java.util.List;

import com.stone.tools.template.field.ITemplateObjectField;

/**
 * The template object;
 * 
 * @author crazyjohn
 *
 */
public class TemplateObject implements ITemplateObject {
	protected String group;
	protected String templateName;
	protected List<ITemplateObjectField> fields;

	public TemplateObject(String group, String templateName, List<ITemplateObjectField> fields) {
		this.group = group;
		this.templateName = templateName;
		this.fields = fields;
	}

	@Override
	public String toString() {
		return super.toString() + " group: \n" + group;
	}

}
