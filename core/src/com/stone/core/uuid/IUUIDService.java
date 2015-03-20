package com.stone.core.uuid;

/**
 * UUID service;
 * 
 * @author crazyjohn
 *
 */
public interface IUUIDService {

	/**
	 * Get next uuid by type;
	 * 
	 * @param uuidType
	 * @return
	 */
	public abstract long getNextUUID(UUIDType uuidType);

}