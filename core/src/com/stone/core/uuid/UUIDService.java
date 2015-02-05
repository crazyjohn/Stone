package com.stone.core.uuid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.db.service.IDBService;

/**
 * UUID管理器
 * 
 */
public class UUIDService implements IUUIDService {

	/** 日志 */
	private static final Logger logger = LoggerFactory
			.getLogger(UUIDService.class);
	/** 每次服务器启动跳过的ID数 */
	private static final int UUID_STEP = 1000;
	/** 查询名前缀 */
	private static final String QUERY_PREFIX = "queryUUID";
	/** 需要处理的UUID类型 */
	private UUIDType[] types;
	/** UUID64 */
	private final UUID64[] uuid64 = new UUID64[UUIDType.values().length];
	/** 数据库代理 */
	private IDBService dbService;
	/** 大区的ID */
	private int rid;
	/** 服的ID */
	private int sid;
	/** 线的ID */
	private int lid;

	public void setDBService(IDBService dbService) {
		this.dbService = dbService;
	}

	public void setUUIDTypes(UUIDType[] types) {
		this.types = types;
	}

	public void setRegionId(int rid) {
		this.rid = rid;
	}

	public void setServerId(int sid) {
		this.sid = sid;
	}

	public void setLineId(int lid) {
		this.lid = lid;
	}

	public long getNextUUID(UUIDType uuidType) {
		return this.uuid64[uuidType.getIndex()].getNextUUID();
	}

	/**
	 * 检查是大区id,服id和线ID是否有效
	 * 
	 * @exception IllegalArgumentException
	 *                当大区id,服id或者线id有一个小于等于0时,会抛出此异常
	 */
	public void check() {
		if (this.rid <= 0 || this.sid <= 0 || this.lid <= 0) {
			throw new IllegalArgumentException(
					"All the rid,sid and the lid must be >0");
		}
	}

	public void init() {
		for (UUIDType _type : types) {
			long _initOid = queryOId(_type.getIndex());
			this.uuid64[_type.getIndex()] = UUID64.buildDefaultUUID(rid, sid,
					lid, _initOid);
			UUID64 _uuid64 = this.uuid64[_type.getIndex()];
			logger.info("Get UUID for [rid:" + this.rid + ",sid:" + this.sid
					+ ",lid:" + this.lid + ",type:" + _type + "] initOid["
					+ _initOid + "] cur uuid[Hex:"
					+ Long.toHexString(_uuid64.getCurUUID()) + " "
					+ _uuid64.getCurUUID() + "]");
		}
	}

	private long queryOId(final int typeIndex) {
		UUIDType _uuidType = UUIDType.valueOf(typeIndex);
		UUID64 _uuid64 = UUID64.buildDefaultUUID(this.rid, this.sid, this.lid,
				0);
		final String _queryName = QUERY_PREFIX + "_" + _uuidType.toString();
		final String[] _paramName = new String[] { "minId", "maxId" };
		final Object[] _paramValues = new Object[] {
				(long) _uuid64.getMinUUID(), (long) _uuid64.getMaxUUID() };
		long _curUUID = 0;
		List<Object> _result = dbService.queryByNameAndParams(_queryName,
				_paramName, _paramValues);
		if (_result == null || _result.isEmpty()) {
			_curUUID = 0;
		} else {
			Object _oid = _result.get(0);
			if (_oid != null) {
				_curUUID = _uuid64.getOid(((Number) (_oid)).longValue());
			}
		}
		_curUUID = _curUUID + UUID_STEP;
		return _curUUID;
	}

}
