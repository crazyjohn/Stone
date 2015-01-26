package com.stone.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.http.annotation.NotThreadSafe;

/**
 * (并发注解)非线程安全单元标记注解;<br>
 * 这类单元要抛到执行的执行线程去执行;<br>
 * 来自jdk并发设计者的推荐模式{@link NotThreadSafe}
 * 
 * @author crazyjohn
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(value = { ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface NotThreadSafeUnit {
	/**
	 * 关于如何保持线程安全的描述;
	 * 
	 * @return
	 */
	String desc() default "";
}
