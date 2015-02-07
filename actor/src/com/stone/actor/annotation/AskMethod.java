package com.stone.actor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The actor conmunicate method;
 * <p>
 * Use ask way, that's means customer want get a return future after this call;
 * 
 * @author crazyjohn
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AskMethod {

}
