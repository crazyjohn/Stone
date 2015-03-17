package com.stone.db.login;

import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.core.converter.IConverter;
import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;
import com.stone.core.util.LRUHashMap;
import com.stone.db.cache.HumanCache;
import com.stone.db.entity.HumanEntity;
import com.stone.db.entity.converter.HumanConverter;
import com.stone.db.msg.internal.DBGetMessage;
import com.stone.db.msg.internal.player.InternalSelectRoleResult;

/**
 * The db human actor;
 * 
 * @author crazyjohn
 *
 */
public class DBHumanActor extends UntypedActor {
	/** human cache */
	private final LRUHashMap<Long, HumanCache> cache;
	/** dbService */
	protected final IDBService dbService;
	/** converter */
	protected final IConverter<HumanEntity, HumanCache> converter;

	public DBHumanActor(int cacheSize, IDBService dbService) {
		cache = new LRUHashMap<Long, HumanCache>(cacheSize, null);
		this.dbService = dbService;
		// converter
		converter = new HumanConverter();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof DBGetMessage) {
			// query human request
			DBGetMessage getMsg = (DBGetMessage) msg;
			onHumanQueryRequest(getMsg);
		}
	}

	/**
	 * Handle get human;
	 * 
	 * @param getMsg
	 */
	private void onHumanQueryRequest(DBGetMessage getMsg) {
		// get cache
		HumanCache humanCache = this.cache.get(getMsg.getId());
		HumanEntity humanEntity = null;
		if (humanCache == null) {
			// load from db
			IEntity entity = this.dbService.get(HumanEntity.class, getMsg.getId());
			if (entity == null) {
				return;
			}
			humanEntity = (HumanEntity) entity;
			// converter to cache
			humanCache = converter.convertFrom(humanEntity);
			// put to cache
			cache.put(humanEntity.getId(), humanCache);
		} else {
			humanEntity = this.converter.convertTo(humanCache);
		}
		// null check
		if (humanEntity == null) {
			return;
		}
		getSender().tell(new InternalSelectRoleResult(humanEntity), getSelf());
	}

	public static Props props(int cacheSize, IDBService dbService) {
		return Props.create(DBHumanActor.class, cacheSize, dbService);
	}

}
