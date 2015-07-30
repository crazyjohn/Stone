package com.stone.core.uuid;

import com.stone.core.data.uuid.IUUID64;
import com.stone.core.data.uuid.UUID64;

public class UUID64Test {

	public static void main(String[] args) {
		IUUID64 uuid = UUID64.buildDefaultUUID(1, 2);
		System.out.println("UUID.id: " + uuid.getCurrentId());
		System.out.println("UUID.nextId: " + uuid.getNextId());
		System.out.println("UUID.nextId: " + uuid.getNextId());
	}

}
