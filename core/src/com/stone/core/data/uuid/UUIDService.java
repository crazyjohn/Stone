package com.stone.core.data.uuid;

import java.util.HashMap;
import java.util.Map;

import akka.actor.Props;

import com.stone.core.actor.AnnotatedUntypedActor;
import com.stone.core.annotation.ActorMethod;
import com.stone.core.db.service.IDBService;

public class UUIDService extends AnnotatedUntypedActor implements IUUIDService {
	protected IDBService dbService;
	private Map<UUIDType, IUUID64> uuids;

	public UUIDService(IDBService dbService, UUIDType[] types, int regionId,
			int serverId) {
		this.dbService = dbService;
		uuids = new HashMap<UUIDType, IUUID64>();
		for (UUIDType eachType : types) {
			// query max id from db
			int maxId = queryMaxIdFromDB(eachType);
			uuids.put(eachType,
					UUID64.buildDefaultUUID(regionId, serverId, maxId));
		}

	}

	@ActorMethod(messageClassType = UUIDType.class)
	protected void handleUUIDRequest(Object msg) {
		if (msg instanceof UUIDType) {
			getSender().tell(getNextId((UUIDType) msg), getSelf());
		}

	}

	private int queryMaxIdFromDB(UUIDType eachType) {
		// TODO Auto-generated method stub
		return 100;
	}

	@Override
	public long getNextId(UUIDType uuidType) {
		IUUID64 uuid = this.uuids.get(uuidType);
		return uuid.getNextId();
	}

	public static Props props(IDBService dbService, UUIDType[] types,
			int regionId, int serverId) {
		return Props.create(UUIDService.class, dbService, types, regionId,
				serverId);
	}
}
