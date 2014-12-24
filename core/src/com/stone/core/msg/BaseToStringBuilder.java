package com.stone.core.msg;

import java.lang.reflect.Field;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 构建toString()方法的基础构建器
 * 
 */
public class BaseToStringBuilder extends ReflectionToStringBuilder {
	private String[] excludeArray;

	public BaseToStringBuilder(Object object, ToStringStyle style,
			String... excludes) {
		super(object, style);
		excludeArray = excludes;
	}

	@Override
	protected boolean accept(Field field) {
		for (String exclude : excludeArray) {
			if (exclude.equals(field.getName())) {
				return false;
			}
		}
		return super.accept(field);
	}
}