/**
 * 
 */
package com.stone.core.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.stone.core.entity.IEntity;

/**
 * 使用Entity自动创建HumanEntityHolder的注解。<p>
 * 
 * 任意实现了<tt>IEntity</tt>接口都可以使用此注解来自动创建HumanEntityHolder的实例，<br>
 * 注意：没有实现IEntity接口的类也可以使用该注解，但是不会产生任何效果。<p>
 * 
 * 使用该注解创建EntityHolder需要手动指定EntityHolder的类名。在实际扫描时，扫描器默认会<br>
 * 在<tt>com.hifun.soul.gamedb.cache.holder</tt>下扫描EntityHolder类，如果指定的<br>
 * EntityHolder类不在上面的包内，需要在指定EntityHolder时包含全限定包名，否则扫描器会在<br>
 * 初始化时抛出<tt>NoClassDefFoundError</tt>错误。<p>
 * 
 * 另外需要注意的是指定的EntityHolder必须实现<tt>IEntityHolder</tt>接口,没有实现<br>
 * <tt>IEntityHolder</tt>接口的EntityHolder在扫描时将抛出<tt>ClassCastException</tt>异常。</b>
 * 
 * 使用方法：<br>
 *  1. 需要自动创建EntityHolder：<pre>
 *  <code>@AutoCreateHumanEntityHolder(EntityHolderClass = "SomeEntityHolder")</code>
 *  <code>public class SomeEntity implements IEntity{...}</code></pre>
 *  <br>
 *  2. 如果需要创建的EntityHolder不在默认的<br>
 *     <tt>com.hifun.soul.gamedb.cache.holder</tt>包下：<pre>
 *  <code>@AutoCreateHumanEntityHolder(EntityHolderClass = "com.package.SomeEntityHolder")</code>
 *  <code>public class SomeEntity implements IEntity{...}</code></pre>
 * 
 * @see IEntity
 * @see EntityScanner
 * 
 * @author crazyjohn
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoCreateHumanEntityHolder {
	String EntityHolderClass();
}
