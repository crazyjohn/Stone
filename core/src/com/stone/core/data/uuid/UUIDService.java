package com.stone.core.data.uuid;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import akka.actor.Props;

import com.stone.core.annotation.ActorMethod;
import com.stone.core.concurrent.annotation.GuardedByUnit;
import com.stone.core.concurrent.annotation.ThreadSafeUnit;
import com.stone.core.db.service.orm.IEntityDBService;

/**
 * The uuidService;
 * <p>
 * Maybe a singletone or just an actor?
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public class UUIDService implements IUUIDService {
	protected IEntityDBService dbService;
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	private Map<UUIDType, IUUID64> uuids;

	public static IUUIDService buildUUIDService(IEntityDBService dbService, UUIDType[] types, int regionId, int serverId) {
		IUUIDService result = new UUIDService(dbService, types, regionId, serverId);
		return result;
	}

	private UUIDService(IEntityDBService dbService, UUIDType[] types, int regionId, int serverId) {
		this.dbService = dbService;
		uuids = new ConcurrentHashMap<UUIDType, IUUID64>();
		for (UUIDType eachType : types) {
			// query max id from db
			long maxId = queryMaxIdFromDB(eachType);
			uuids.put(eachType, UUID64.buildDefaultUUID(regionId, serverId, maxId));
		}

	}

	@ActorMethod(messageClassType = UUIDType.class)
	protected void handleUUIDRequest(Object msg) {
		if (msg instanceof UUIDType) {
			// getSender().tell(getNextId((UUIDType) msg), getSelf());
		}

	}

	private long queryMaxIdFromDB(UUIDType uuidType) {
		List<Long> result = dbService.queryByNameAndParams(getQueryName(uuidType), new String[0], new Object[0]);
		return result.get(0);
	}

	private String getQueryName(UUIDType uuidType) {
		return "QUERY_" + uuidType + "_MAX_ID";
	}

	@Override
	public long getNextId(UUIDType uuidType) {
		IUUID64 uuid = this.uuids.get(uuidType);
		return uuid.getNextId();
	}

	public static Props props(IEntityDBService dbService, UUIDType[] types, int regionId, int serverId) {
		return Props.create(UUIDService.class, dbService, types, regionId, serverId);
	}
}
