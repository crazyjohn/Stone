package com.stone.core.uuid.msg;

import com.stone.core.data.uuid.UUIDType;

/**
 * Internal get uuid message;
 * 
 * @author crazyjohn
 *
 */
public class InternalGetUUID {
	private final UUIDType uuidType;

	public InternalGetUUID(UUIDType uuidType) {
		this.uuidType = uuidType;
	}

	public UUIDType getUuidType() {
		return uuidType;
	}
}
