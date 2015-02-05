package com.stone.core.annotation;


/**
 * Annotation过滤器，用来过滤某个类上是否标记annotation。<p>
 * 
 * 使用方法：<br>
 * 假设我们需要过滤出标记有A这个annotation的类：<pre>
 * <code>new AnnotationFilter(){</code>
 * <code>  @Override</code>
 * <code>  public boolean accept(Class<?> aClass){</code>
 * <code>    return aClass.getAnnotation(A.class) != null;</code>
 * <code>  }</code>
 * <code>}</code></pre>
 *
 * @author crazyjohn
 * 
 */
public interface IAnnotationFilter {
	
	/**
	 * 该方法用来过滤某个类是否满足指定的条件。
	 * 
	 * @param aClass
	 * @return
	 */
	boolean accept(Class<?> aClass);
}
