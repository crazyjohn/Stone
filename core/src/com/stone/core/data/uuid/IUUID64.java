package com.stone.core.data.uuid;

/**
 * The UUID;
 * 
 * @author crazyjohn
 *
 */
public interface IUUID64 {

	public long getNextId();

	public long getCurrentId();

	public int getRegionId(long uuid);

	public int getServerId(long uuid);
}
