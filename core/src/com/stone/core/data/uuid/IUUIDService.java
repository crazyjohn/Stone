package com.stone.core.data.uuid;

/**
 * The UUIDService;
 * 
 * @author crazyjohn
 *
 */
public interface IUUIDService {

	/**
	 * Get next id by UUIDType;
	 * 
	 * @param uuidType
	 * @return
	 */
	public long getNextId(UUIDType uuidType);
}
