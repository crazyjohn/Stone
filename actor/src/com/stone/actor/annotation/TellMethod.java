package com.stone.actor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The actor conmunicate method;
 * <p>
 * Use tell way, that's means you don't care about any return value, just fire
 * and forget;
 * 
 * @author crazyjohn
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TellMethod {

}
