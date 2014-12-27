package com.stone.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotifyUpdate {
	/**
	 * 是否异步调用;
	 * 
	 * @return
	 */
	public boolean async();
}
