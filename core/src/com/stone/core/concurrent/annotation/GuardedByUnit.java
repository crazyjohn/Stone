package com.stone.core.concurrent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This will tell you which monitor protect the target data unit;
 * 
 * @author crazyjohn
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(value = { ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface GuardedByUnit {
	/**
	 * The lock which protect me;
	 * 
	 * @return
	 */
	String whoCareMe();
}
