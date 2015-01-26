package com.stone.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.http.annotation.ThreadSafe;

/**
 * (并发注解)线程安全单元标记注解;<br>
 * 来自jdk并发设计者的推荐模式{@link ThreadSafe}
 * 
 * @author crazyjohn
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(value = { ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface ThreadSafeUnit {
	/**
	 * 是否是异步执行的;<br>
	 * 如果是异步执行, 调用的时候要切记本次调用的后续逻辑不依赖这个单元的执行结果, 否则会有异步引发的值不一致的情况;
	 * 
	 * @return true 表示异步执行, false 表示同步执行;
	 */
	boolean isAsync();
}
