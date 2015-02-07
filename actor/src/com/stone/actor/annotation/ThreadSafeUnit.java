package com.stone.actor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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

}
