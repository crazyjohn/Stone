package com.stone.core.jvm;

/**
 * The class loader tree;
 * 
 * @author crazyjohn
 *
 */
public class ClassLoaderTree {

	public static void main(String[] args) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		while (loader != null) {
			System.out.println(loader);
			loader = loader.getParent();
		}
	}

}
