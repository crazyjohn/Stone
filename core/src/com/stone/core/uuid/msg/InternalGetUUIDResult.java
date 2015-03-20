package com.stone.core.uuid.msg;

/**
 * Get UUID result;
 * 
 * @author crazyjohn
 *
 */
public class InternalGetUUIDResult {
	private final long id;

	public InternalGetUUIDResult(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

}
