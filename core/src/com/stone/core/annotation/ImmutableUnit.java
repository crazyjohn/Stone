package com.stone.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.http.annotation.Immutable;

/**
 * (并发注解)不可变对象标记;<br>
 * 来自jdk并发设计者的推荐模式{@link Immutable}
 * 
 * @author crazyjohn
 *
 */
@Retention(RetentionPolicy.CLASS)
@Target(value = { ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface ImmutableUnit {

}
