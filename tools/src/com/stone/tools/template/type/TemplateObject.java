package com.stone.tools.template.type;

import java.util.Collections;
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
		StringBuilder sb = new StringBuilder("{").append(this.templateName).append(":\n");
		for (ITemplateObjectField field : fields) {
			sb.append(field.toString() + "\n");
		}
		sb.append("}");
		return sb.toString();
	}

	@Override
	public List<ITemplateObjectField> getAllFileds() {
		return Collections.unmodifiableList(fields);
	}

	@Override
	public String getName() {
		return templateName;
	}

}
