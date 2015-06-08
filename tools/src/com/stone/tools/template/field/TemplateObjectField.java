package com.stone.tools.template.field;

/**
 * The template object's field;
 * 
 * @author crazyjohn
 *
 */
public class TemplateObjectField implements ITemplateObjectField {
	protected String fieldType;
	protected String fieldName;
	protected String comment;

	public TemplateObjectField(String fieldType, String fieldName, String comment) {
		this.fieldType = fieldType;
		this.fieldName = fieldName;
		this.comment = comment;
	}

	@Override
	public FieldType getType() {
		return FieldType.typeOf(this.fieldType);
	}

	@Override
	public String getName() {
		return fieldName;
	}

	@Override
	public String getComment() {
		return comment;
	}

	@Override
	public String toString() {
		return this.fieldName + " " + this.fieldType + "; // " + this.comment;
	}

}
