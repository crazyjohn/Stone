package com.stone.core.uuid;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The UUID struct;
 * <p>
 * region(5) + server(10) + line(6) + objectId(38) + other(4) = 63bits;
 * 
 * @author crazyjohn
 *
 */
public class UUID64 {
	/** 有效的位数 */
	private static final int MAX_BITS = 63;
	/** 大区ID的位数 */
	private final int ridBits;
	/** 服ID的位数 */
	private final int sidBits;
	/** 线ID的位数 */
	private final int lidBits;
	/** 对象ID的位数 */
	private final int oidBits;
	/** 大区的ID */
	private final long rid;
	/** 服的ID */
	private final long sid;
	/** 线的ID */
	private final long lid;
	/** 大区ID的掩码 */
	private final long ridMask;
	/** 服ID的掩码 */
	private final long sidMask;
	/** 线ID的掩码 */
	private final long lidMask;
	/** oid的掩码 */
	private final long oidMask;
	/** 对象的ID */
	private final AtomicLong oid;
	/** 当前UUID所能表示的最大值 */
	private final long maxUUID;
	/** 当前UUID的最小值 */
	private final long minUUID;

	/**
	 * 
	 * @param ridBits
	 *            大区ID的bit位数,至少要1位
	 * @param sidBits
	 *            服ID的bit位数,至少要1位
	 * @param lidBits
	 *            线ID的bit位数,至少要1位
	 * @param oidBits
	 *            对象ID的bit数,至少要1位,1位的id没啥用
	 * @param rid
	 * @param sid
	 * @param lid
	 * @param initOid
	 * @exception IllegalArgumentException
	 *                如果参数错误,会抛出此异常
	 */
	UUID64(int ridBits, int sidBits, int lidBits, int oidBits, long rid, long sid, long lid, long initOid) {
		if (ridBits < 1 || sidBits < 1 || lidBits < 1 || oidBits < 1) {
			throw new IllegalArgumentException("The ridBits,sidBits,lidBits and the oidBits must be >= 1");
		}
		if ((ridBits + sidBits + lidBits + oidBits) > MAX_BITS) {
			throw new IllegalArgumentException("Only 63 bits for all the bits part");
		}
		if (rid < 0 || sid < 0 || lid < 0) {
			throw new IllegalArgumentException("All the rid,sid and the lid  must be >0 ");
		}
		if (initOid < 0) {
			throw new IllegalArgumentException("The initOid must   must be >= 0 ");
		}

		checkBitsMaxValue(ridBits, rid);
		checkBitsMaxValue(sidBits, sid);
		checkBitsMaxValue(lidBits, lid);
		checkBitsMaxValue(oidBits, initOid);

		this.ridBits = ridBits;
		this.sidBits = sidBits;
		this.lidBits = lidBits;
		this.oidBits = oidBits;

		this.rid = rid;
		this.sid = sid;
		this.lid = lid;

		int leftBits = MAX_BITS - ridBits;
		long high = rid << leftBits;
		ridMask = getMaskBits(ridBits, leftBits);

		leftBits -= sidBits;
		high = high | (sid << leftBits);
		sidMask = getMaskBits(sidBits, leftBits);

		leftBits -= lidBits;
		high = high | (lid << leftBits);
		lidMask = getMaskBits(lidBits, leftBits);

		this.oidMask = getMaskBits(this.oidBits, 0);
		this.minUUID = high;
		this.maxUUID = high | this.oidMask;

		this.oid = new AtomicLong(high | initOid);
		if (this.oid.get() >= this.maxUUID) {
			throw new IllegalArgumentException("The oid [" + this.oid.get() + "] has reach the maxUUID [" + this.maxUUID + "]");
		}
	}

	/**
	 * Get the next UUID value;
	 * 
	 * @return
	 * @exception IllegalStateException
	 *                如果当前取得的UUID大于{@link #maxUUID},表示发生了溢出,会抛出此异常
	 */
	public long getNextUUID() {
		final long nextId = this.oid.incrementAndGet();
		if (nextId > this.maxUUID) {
			this.oid.set(this.maxUUID);
			throw new IllegalStateException("The uuid has been overflow,curId [" + nextId + "] maxUUID [" + this.maxUUID + "]");
		}
		return nextId;
	}

	/**
	 * Get the current UUID value;
	 * 
	 * @return
	 */
	protected long getCurUUID() {
		final long currentId = this.oid.get();
		if (currentId > this.maxUUID) {
			this.oid.set(this.maxUUID);
			throw new IllegalStateException("The uuid has been overflow,curId [" + currentId + "] maxUUID [" + this.maxUUID + "]");
		}
		return currentId;
	}

	protected long getRid() {
		return rid;
	}

	/**
	 * Get the regionId part from the UUID;
	 * 
	 * @param uuid
	 * @return
	 */
	protected long getRid(final long uuid) {
		return (this.ridMask & uuid) >> (MAX_BITS - this.ridBits);
	}

	protected long getSid() {
		return sid;
	}

	/**
	 * Get the serverId part from the UUID;
	 * 
	 * @param uuid
	 * @return
	 */
	protected long getSid(final long uuid) {
		return (this.sidMask & uuid) >> (MAX_BITS - this.ridBits - this.sidBits);
	}

	protected long getLid() {
		return lid;
	}

	/**
	 * Get the lineId part from the UUID;
	 * 
	 * @param uuid
	 * @return
	 */
	protected long getLid(final long uuid) {
		return (this.lidMask & uuid) >> (MAX_BITS - this.ridBits - this.sidBits - this.lidBits);
	}

	/**
	 * Get the objectID part from the UUID;
	 * 
	 * @param uuid
	 * @return
	 */
	public long getOid(final long uuid) {
		return this.oidMask & uuid;
	}

	/**
	 * Get the max UUID;
	 * 
	 * @return
	 */
	public long getMaxUUID() {
		return maxUUID;
	}

	/**
	 * Get the min UUID;
	 * 
	 * @return
	 */
	public long getMinUUID() {
		return minUUID;
	}

	/**
	 * 
	 * 构建一个系统默认的UUID64实例,5位rid,10位sid,6位lid,38位oid,在oid之前保留4位
	 * 
	 * @param rid
	 *            regionId
	 * @param sid
	 *            serverId
	 * @param lid
	 *            lineId
	 * @param initOid
	 *            initial object id
	 * @return
	 */
	public static UUID64 buildDefaultUUID(final int rid, final int sid, final int lid, final long initOid) {
		return new UUID64(5, 10, 6, 38, rid, sid, lid, initOid);
	}

	/**
	 * 检查指定的value是否超出了bits可以表示的范围
	 * 
	 * @param bits
	 * @param value
	 */
	private void checkBitsMaxValue(int bits, long value) {
		long bitMaxValue = getBitsMaxValue(bits);
		if (value > bitMaxValue) {
			throw new IllegalArgumentException("Can't represent value [" + value + "] with " + bits + " bits");
		}
	}

	/**
	 * 取得指定bits左移指定位数leftShiftBits后的最大值
	 * 
	 * @param bits
	 * @param leftShiftBits
	 * @return
	 * @exception IllegalArgumentException
	 */
	private long getMaskBits(int bits, int leftShiftBits) {
		long bitMaxValue = getBitsMaxValue(bits);
		if (bitMaxValue <= 0) {
			throw new IllegalArgumentException("Bad bits [" + bits + "]");
		}
		return Long.MAX_VALUE & (bitMaxValue << leftShiftBits);
	}

	/**
	 * Get the bits max value;
	 * 
	 * @param bits
	 * @return
	 */
	private long getBitsMaxValue(int bits) {
		return (1L << bits) - 1;
	}
}
