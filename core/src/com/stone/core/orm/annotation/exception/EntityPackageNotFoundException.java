package com.stone.core.orm.annotation.exception;

/**
 * EntityScanner扫描无法获得Package路径时的异常。
 * 
 * @author crazyjohn
 */
public class EntityPackageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityPackageNotFoundException(Throwable t) {
		super("entity package not found!", t);
	}
}
