package com.stone.tools.template.field;

/**
 * The template object's field;
 * 
 * @author crazyjohn
 *
 */
public interface ITemplateObjectField {
	/**
	 * Field Type;
	 * 
	 * @author crazyjohn
	 *
	 */
	enum FieldType {
		SHORT, INT, LONG, FLOAT, DOUBLE, STRING, ARRAY, LIST, SET, MAP;

		public static FieldType typeOf(String fieldType) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * Get the field type;
	 * 
	 * @return
	 */
	public FieldType getType();

	/**
	 * Get the fieldName;
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Get the field comment;
	 * 
	 * @return
	 */
	public String getComment();
}
