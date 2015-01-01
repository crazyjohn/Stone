package com.stone.core.data;

/**
 * DB操作回调接口;
 * 
 * @author crazyjohn
 *
 * @param <T>
 */
public interface IDBCallback<T> {
	public void callback(T result);
}
