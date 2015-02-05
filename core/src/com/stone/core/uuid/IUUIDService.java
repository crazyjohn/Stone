package com.stone.core.uuid;

/**
 * UUID service;
 * 
 * @author crazyjohn
 *
 */
public interface IUUIDService {

	/**
	 * 取得指定类型的下一个UUID
	 * 
	 * @param uuidType
	 * @return
	 */
	public abstract long getNextUUID(UUIDType uuidType);

}