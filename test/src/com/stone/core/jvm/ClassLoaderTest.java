package com.stone.core.jvm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class loader test;
 * 
 * @author crazyjohn
 *
 */
public class ClassLoaderTest {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException,
			SecurityException, IllegalArgumentException, InvocationTargetException {
		FileSystemClassLoader loader1 = new FileSystemClassLoader("resources\\classes\\");
		FileSystemClassLoader loader2 = new FileSystemClassLoader("resources\\classes\\");
		// class1
		Class<?> class1 = loader1.loadClass("com.stone.core.jvm.Sample");
		Object sample1 = class1.newInstance();
		Method method = class1.getMethod("call");
		method.invoke(sample1);
		// class2
		Class<?> class2 = loader2.loadClass("com.stone.core.jvm.Sample");
		Object sample2 = class2.newInstance();
		Method method2 = class2.getMethod("setInstance", java.lang.Object.class);
		method2.invoke(sample2, sample1);
	}

}
