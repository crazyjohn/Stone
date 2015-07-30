package com.stone.core.uuid;

import com.stone.core.data.uuid.IUUID64;
import com.stone.core.data.uuid.UUID64;

public class UUID64Test {

	public static void main(String[] args) {
		IUUID64 uuid = UUID64.buildDefaultUUID(1, 2, 1);
		System.out.println("UUID.id: " + uuid.getCurrentId());
		System.out.println("UUID.nextId: " + uuid.getNextId());
		System.out.println("UUID.nextId: " + uuid.getNextId());
		long currentId = uuid.getCurrentId();
		System.out.println("UUID.id: " + currentId);
		// regionId
		System.out.println("RegionId: " + uuid.getRegionId(currentId));
		System.out.println("ServerId: " + uuid.getServerId(currentId));
		// uuid 2
		IUUID64 uuid2 = UUID64.buildDefaultUUID(2, 101, 1);
		System.out.println("uuid2.id: " + uuid2.getCurrentId());
		System.out.println("uuid2.nextId: " + uuid2.getNextId());
		System.out.println("uuid2.nextId: " + uuid2.getNextId());
		long currentId2 = uuid2.getCurrentId();
		System.out.println("uuid2.id: " + currentId2);
		// regionId
		System.out.println("RegionId: " + uuid.getRegionId(currentId2));
		System.out.println("ServerId: " + uuid.getServerId(currentId2));
	}

}
