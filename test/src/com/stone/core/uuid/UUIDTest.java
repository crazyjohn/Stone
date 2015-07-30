package com.stone.core.uuid;

import java.util.concurrent.atomic.AtomicLong;

public class UUIDTest {
	private static AtomicLong idGenerator;
	private static int bits = 63;
	// bits
	private static int regionBits = 5;
	private static int serverBits = 15;
	private static int objectBits = 40;
	private static long regionIdMask;
	private static long serverIdMask;

	public static void main(String[] args) {
		int initValue = 1;
		int regionId = 1;
		int serverId = 2;
		long objectId = new AtomicLong(100).get();

		// high
		long high = getRealValue(regionId, (bits - regionBits));
		high = high | getRealValue(serverId, (bits - regionBits - serverBits));
		// high = high | getRealValue(objectId, (bits - regionBits - serverBits
		// - objectBits));
		System.out.println("real regionId: "
				+ getRealValue(regionId, (bits - regionBits)));
		System.out.println("real serverId: "
				+ getRealValue(serverId, (bits - regionBits - serverBits)));
		System.out.println("real objectId: "
				+ getRealValue(objectId,
						(bits - regionBits - serverBits - objectBits)));
		// high
		System.out.println("high: " + (high | initValue));
		// nextId
		System.out.println("nextId: " + getNextUUID(regionId, serverId));
		System.out.println("nextId: " + getNextUUID(regionId, serverId));
		System.out.println("nextId: " + getNextUUID(regionId, serverId));
		// parse
		long uuid = getNextUUID(regionId, serverId);
		System.out.println("regionId: " + getRegionId(uuid));
		System.out.println("serverId: " + getServerId(uuid));
	}

	private static long getRealValue(long id, int bits) {
		return id << bits;
	}

	private static long getMask(int bits, int shitBits) {
		long maxValue = (1 << bits) - 1;
		return maxValue << shitBits;
	}

	protected static long getNextUUID(int regionId, int serverId) {
		int initValue = 0;
		// high
		long high = getRealValue(regionId, (bits - regionBits));
		regionIdMask = getMask(regionBits, (bits - regionBits));
		high = high | getRealValue(serverId, (bits - regionBits - serverBits));
		serverIdMask = getMask(serverBits, (bits - regionBits - serverBits));
		if (idGenerator == null) {
			idGenerator = new AtomicLong(high | initValue);
		}
		// high = high
		// | getRealValue(idGenerator.incrementAndGet(), (bits
		// - regionBits - serverBits - objectBits));
		return idGenerator.incrementAndGet();
	}

	protected static int getRegionId(long uuid) {
		return (int) ((regionIdMask & uuid) >> (bits - regionBits));
	}

	protected static int getServerId(long uuid) {
		return (int) ((serverIdMask & uuid) >> (bits - regionBits - serverBits));
	}

}
