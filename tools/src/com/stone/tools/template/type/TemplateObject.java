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
	/** the template's regular string group */
	protected String group;
	/** the template's name */
	protected String templateName;
	/** the template's comment */
	protected String comment;
	protected List<ITemplateObjectField> fields;

	public TemplateObject(String group, String templateName, String comment,
			List<ITemplateObjectField> fields) {
		this.group = group;
		this.templateName = templateName;
		this.fields = fields;
		this.comment = comment;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{").append(this.templateName)
				.append(":\n");
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

	@Override
	public String getComment() {
		return comment;
	}

}
