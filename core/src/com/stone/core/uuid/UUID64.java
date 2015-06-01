package com.stone.core.uuid;

import java.util.concurrent.atomic.AtomicLong;

/**
 * UUID类,按分区/服/线实现的64位的UUID
 * 
 * 
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

		checkBitsMaxNum(ridBits, rid);
		checkBitsMaxNum(sidBits, sid);
		checkBitsMaxNum(lidBits, lid);
		checkBitsMaxNum(oidBits, initOid);

		this.ridBits = ridBits;
		this.sidBits = sidBits;
		this.lidBits = lidBits;
		this.oidBits = oidBits;

		this.rid = rid;
		this.sid = sid;
		this.lid = lid;

		int _leftShiftBits = MAX_BITS - ridBits;
		long _high = rid << _leftShiftBits;
		ridMask = getMaskBits(ridBits, _leftShiftBits);

		_leftShiftBits -= sidBits;
		_high = _high | (sid << _leftShiftBits);
		sidMask = getMaskBits(sidBits, _leftShiftBits);

		_leftShiftBits -= lidBits;
		_high = _high | (lid << _leftShiftBits);
		lidMask = getMaskBits(lidBits, _leftShiftBits);

		this.oidMask = getMaskBits(this.oidBits, 0);
		this.minUUID = _high;
		this.maxUUID = _high | this.oidMask;

		this.oid = new AtomicLong(_high | initOid);
		if (this.oid.get() >= this.maxUUID) {
			throw new IllegalArgumentException("The oid [" + this.oid.get() + "] has reach the maxUUID [" + this.maxUUID + "]");
		}
	}

	/**
	 * 取得一个递增的UUID
	 * 
	 * @return
	 * @exception IllegalStateException
	 *                如果当前取得的UUID大于{@link #maxUUID},表示发生了溢出,会抛出此异常
	 */
	public long getNextUUID() {
		final long _curId = this.oid.incrementAndGet();
		if (_curId > this.maxUUID) {
			this.oid.set(this.maxUUID);
			throw new IllegalStateException("The uuid has been overflow,curId [" + _curId + "] maxUUID [" + this.maxUUID + "]");
		}
		return _curId;
	}

	/**
	 * 取得当前的UUID
	 * 
	 * @return
	 */
	protected long getCurUUID() {
		final long _curId = this.oid.get();
		if (_curId > this.maxUUID) {
			this.oid.set(this.maxUUID);
			throw new IllegalStateException("The uuid has been overflow,curId [" + _curId + "] maxUUID [" + this.maxUUID + "]");
		}
		return _curId;
	}

	protected long getRid() {
		return rid;
	}

	/**
	 * 获取指定UUID的大区ID
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
	 * 获取指定UUID的服ID
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
	 * 获取指定UUID的线ID
	 * 
	 * @param uuid
	 * @return
	 */
	protected long getLid(final long uuid) {
		return (this.lidMask & uuid) >> (MAX_BITS - this.ridBits - this.sidBits - this.lidBits);
	}

	/**
	 * 获取指定UUID的oid部分
	 * 
	 * @param uuid
	 * @return
	 */
	public long getOid(final long uuid) {
		return this.oidMask & uuid;
	}

	/**
	 * @return the maxUUID
	 */
	public long getMaxUUID() {
		return maxUUID;
	}

	/**
	 * @return the minUUID
	 */
	public long getMinUUID() {
		return minUUID;
	}

	/**
	 * 
	 * 构建一个系统默认的UUID64实例,5位rid,10位sid,6位lid,38位oid,在oid之前保留4位
	 * 
	 * @param rid
	 *            大区的ID
	 * @param sid
	 *            服的ID
	 * @param lid
	 *            线的ID
	 * @param initOid
	 *            初始的对象id
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
	private void checkBitsMaxNum(int bits, long value) {
		long _bitMax = (1L << bits);
		if (value >= _bitMax) {
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
		long _max = (1L << bits) - 1;
		if (_max <= 0) {
			throw new IllegalArgumentException("Bad bits [" + bits + "]");
		}
		return Long.MAX_VALUE & (_max << leftShiftBits);
	}
}
