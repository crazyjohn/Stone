package com.stone.tools.template;

import java.util.List;

/**
 * 字段对象
 * 
 * 
 */
public class ExcelFieldObject {

	/** 类型 */
	private String fieldType;
	/** 名字 */
	private String fieldName;
	/** 第一个字母大写的名字 */
	private String bigName;
	/** 注解 */
	private List<String> anotations;
	/** 注释 */
	private String comment;
	/** 是否为X坐标 */
	private boolean xpoint;
	/** 是否为Y坐标 */
	private boolean ypoint;
	/** 最大值 */
	private int maxValue;
	/** 最小值 */
	private int minValue;
	/** 是否允许为空 */
	private boolean notNull;
	/** 起始行数 */
	private int startLine;
	/** 最大长度 */
	private int maxLen;
	/** 最小长度 */
	private int minLen;

	/**
	 * @param fieldType
	 * @param fieldName
	 * @param anotations
	 * @param comment
	 * @param xpoint
	 * @param ypoint
	 */
	public ExcelFieldObject(String fieldType, String fieldName,
			List<String> anotations, String comment, boolean xpoint,
			boolean ypoint, int maxValue, int minValue, boolean notNull,
			int startLine, int maxLen, int minLen) {
		super();
		this.fieldType = fieldType;
		this.fieldName = fieldName;
		this.anotations = anotations;
		this.comment = comment;
		this.xpoint = xpoint;
		this.ypoint = ypoint;
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.notNull = notNull;
		this.startLine = startLine;
		this.maxLen = maxLen;
		this.minLen = minLen;
		// 首字母大写
		this.bigName = fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		if (xpoint || ypoint) {
			if (minValue == -1) {
				this.minValue = 0;
			}
			if (maxValue == -1) {
				this.maxValue = 1000;
			}
		}

	}

	public String getFieldType() {
		return fieldType;
	}

	public String getFieldName() {
		return fieldName;
	}

	public List<String> getAnotations() {
		return anotations;
	}

	public String getComment() {
		return comment;
	}

	public String getBigName() {
		return bigName;
	}

	public boolean isXpoint() {
		return xpoint;
	}

	public boolean isYpoint() {
		return ypoint;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public int getMinValue() {
		return minValue;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public int getStartLine() {
		return startLine;
	}

	public int getMaxLen() {
		return maxLen;
	}

	public int getMinLen() {
		return minLen;
	}

}
