package com.stone.core.uuid;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;

import com.stone.core.db.service.IDBService;
import com.stone.core.uuid.msg.InternalGetUUID;
import com.stone.core.uuid.msg.InternalGetUUIDResult;

/**
 * The uuid service;
 * 
 * @author crazyjohn
 *
 */
public class UUIDService extends UntypedActor implements IUUIDService {

	/** logger */
	private static final Logger logger = LoggerFactory.getLogger(UUIDService.class);
	/** 每次服务器启动跳过的ID数 */
	private static final int UUID_STEP = 1000;
	/** 查询名前缀 */
	private static final String QUERY_PREFIX = "queryUUID";
	/** 需要处理的UUID类型 */
	private final UUIDType[] types;
	/** UUID64 */
	private final UUID64[] uuid64 = new UUID64[UUIDType.values().length];
	/** 数据库代理 */
	private final IDBService dbService;
	/** 大区的ID */
	private final int regionId;
	/** 服的ID */
	private final int serverId;
	/** 线的ID */
	private final int lineId;

	public UUIDService(IDBService dbService, UUIDType[] types, int regionId, int serverId, int lineId) {
		this.dbService = dbService;
		this.types = types;
		this.regionId = regionId;
		this.serverId = serverId;
		this.lineId = lineId;
	}

	public long getNextUUID(UUIDType uuidType) {
		return this.uuid64[uuidType.getType()].getNextUUID();
	}

	/**
	 * 检查是大区id,服id和线ID是否有效
	 * 
	 * @exception IllegalArgumentException
	 *                当大区id,服id或者线id有一个小于等于0时,会抛出此异常
	 */
	protected void check() {
		if (this.regionId <= 0 || this.serverId <= 0 || this.lineId <= 0) {
			throw new IllegalArgumentException("All the rid,sid and the lid must be >0");
		}
	}

	protected void init() {
		for (UUIDType _type : types) {
			long _initOid = queryOId(_type.getType());
			this.uuid64[_type.getType()] = UUID64.buildDefaultUUID(regionId, serverId, lineId, _initOid);
			UUID64 _uuid64 = this.uuid64[_type.getType()];
			logger.info("Get UUID for [rid:" + this.regionId + ",sid:" + this.serverId + ",lid:" + this.lineId + ",type:" + _type + "] initOid["
					+ _initOid + "] cur uuid[Hex:" + Long.toHexString(_uuid64.getCurUUID()) + " " + _uuid64.getCurUUID() + "]");
		}
	}

	private long queryOId(final int typeIndex) {
		UUIDType _uuidType = UUIDType.typeOf(typeIndex);
		UUID64 _uuid64 = UUID64.buildDefaultUUID(this.regionId, this.serverId, this.lineId, 0);
		final String _queryName = QUERY_PREFIX + "_" + _uuidType.toString();
		final String[] _paramName = new String[] { "minId", "maxId" };
		final Object[] _paramValues = new Object[] { (long) _uuid64.getMinUUID(), (long) _uuid64.getMaxUUID() };
		long _curUUID = 0;
		List<Object> _result = dbService.queryByNameAndParams(_queryName, _paramName, _paramValues);
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

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof InternalGetUUID) {
			InternalGetUUID getUUIDMsg = (InternalGetUUID) msg;
			UUIDType type = getUUIDMsg.getUuidType();
			long nextId = this.getNextUUID(type);
			this.getSender().tell(new InternalGetUUIDResult(nextId), this.getSelf());
		}
	}

}
