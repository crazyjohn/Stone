package com.stone.actor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * (并发注解)被哪个锁保护标记;<br>
 * 来自jdk并发设计者的推荐模式{@link GuardedBy}
 * 
 * @author crazyjohn
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(value = { ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface GuardedByUnit {
	/**
	 * 保护我的锁描述;
	 * 
	 * @return
	 */
	String whoCareMe();
}
