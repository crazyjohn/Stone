package com.stone.db.login;

import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.core.converter.IConverter;
import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;
import com.stone.core.util.LRUHashMap;
import com.stone.db.cache.HumanCache;
import com.stone.db.entity.HumanEntity;
import com.stone.db.entity.HumanItemEntity;
import com.stone.db.msg.internal.DBGetMessage;
import com.stone.db.msg.internal.player.InternalSelectRoleResult;
import com.stone.proto.entity.Entities.HumanItemData;

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
			DBGetMessage getMsg = (DBGetMessage) msg;
			handleGetHuman(getMsg);
		}
	}

	/**
	 * Handle get human;
	 * 
	 * @param getMsg
	 */
	private void handleGetHuman(DBGetMessage getMsg) {
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

	/**
	 * Converter;
	 * 
	 * @author crazyjohn
	 *
	 */
	static class HumanConverter implements IConverter<HumanEntity, HumanCache> {

		@Override
		public HumanCache convertFrom(HumanEntity entity) {
			HumanCache cache = new HumanCache();
			cache.setHumanGuid(entity.getGuid());
			// add item
			for (HumanItemData eachItem : entity.getBuilder().getHumanItemsList()) {
				HumanItemEntity itemEntity = new HumanItemEntity(eachItem.toBuilder());
				cache.add(itemEntity);
			}
			return cache;
		}

		@Override
		public HumanEntity convertTo(HumanCache toObject) {
			HumanEntity entity = new HumanEntity();
			entity.setGuid(toObject.getHumanGuid());
			// item
			for (HumanItemEntity itemEntity : toObject
					.getEntites(HumanItemEntity.class)) {
				entity.getBuilder().addHumanItems(itemEntity.getBuilder().clone());
			}
			return entity;
		}

	}

}
