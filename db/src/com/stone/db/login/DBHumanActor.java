package com.stone.db.login;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.concurrent.duration.Duration;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.core.converter.IConverter;
import com.stone.core.db.service.IDBService;
import com.stone.core.entity.IEntity;
import com.stone.core.entity.IHumanSubEntity;
import com.stone.core.util.LRUHashMap;
import com.stone.db.cache.HumanCache;
import com.stone.db.entity.HumanEntity;
import com.stone.db.entity.converter.HumanConverter;
import com.stone.db.msg.internal.DBDeleteMessage;
import com.stone.db.msg.internal.DBGetMessage;
import com.stone.db.msg.internal.DBUpdateMessage;
import com.stone.db.msg.internal.player.InternalSelectRoleResult;

/**
 * The db human actor;
 * 
 * @author crazyjohn
 *
 */
public class DBHumanActor extends UntypedActor {
	private static final String TICK = "tick";
	private static final int BATCH_UPDATE_COUNT = 100;
	/** human cache */
	private final LRUHashMap<Long, HumanCache> cache;
	/** dbService */
	protected final IDBService dbService;
	/** converter */
	protected final IConverter<HumanEntity, HumanCache> converter;
	/** logger */
	private Logger logger = LoggerFactory.getLogger(DBHumanActor.class);

	public DBHumanActor(int cacheSize, IDBService dbService) {
		cache = new LRUHashMap<Long, HumanCache>(cacheSize, null);
		this.dbService = dbService;
		// converter
		converter = new HumanConverter();
		// schedule
		this.getContext()
				.system()
				.scheduler()
				.schedule(Duration.create(50, TimeUnit.MILLISECONDS), Duration.create(10, TimeUnit.SECONDS), this.getSelf(), TICK,
						this.getContext().system().dispatcher(), this.getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof DBGetMessage) {
			// query human request
			DBGetMessage getMsg = (DBGetMessage) msg;
			onHumanQueryRequest(getMsg);
		} else if (msg.equals(TICK)) {
			// handle tick request
			logger.error("Time to update the dirty human cache.");
			handleTickRequest();
		} else if (msg instanceof DBUpdateMessage) {
			DBUpdateMessage updateMsg = (DBUpdateMessage) msg;
			// handle update request
			handleUpdateRequest(updateMsg);

		} else if (msg instanceof DBDeleteMessage) {
			DBDeleteMessage deleteMsg = (DBDeleteMessage) msg;
			// handle delete request
			handleDeleteRequest(deleteMsg);
		}
	}

	/**
	 * Handle delete request;
	 * 
	 * @param deleteMsg
	 */
	private void handleDeleteRequest(DBDeleteMessage deleteMsg) {
		if (deleteMsg.getEntity() instanceof IHumanSubEntity) {
			// handle sub human entity
			IHumanSubEntity humanSubEntity = (IHumanSubEntity) deleteMsg.getEntity();
			HumanCache humanCache = this.cache.get(humanSubEntity.getHumanGuid());
			if (humanCache == null) {
				logger.warn(String.format("Human cache is null, the humaGuid is: %d", humanSubEntity.getHumanGuid()));
			}
			// remove sub entity
			humanCache.remove(humanSubEntity.getClass(), humanSubEntity.getId());
		} else {
			logger.warn(String.format("MFucker are you kidding me? DBHumanActor do not handle this class: %s", deleteMsg.getEntityClass()
					.getSimpleName()));
		}
	}

	/**
	 * Handle tick request;
	 */
	private void handleTickRequest() {
		// not a good way
		int updateCount = 0;
		for (HumanCache eachCache : this.cache.values()) {
			// break when up to max update count
			if (updateCount >= BATCH_UPDATE_COUNT) {
				break;
			}
			if (eachCache.isModified()) {
				HumanEntity eachEntity = this.converter.convertTo(eachCache);
				this.dbService.update(eachEntity);
				eachCache.resetModified();
				updateCount++;
			}
		}
	}

	/**
	 * Handle update request;
	 * 
	 * @param updateMsg
	 */
	private void handleUpdateRequest(DBUpdateMessage updateMsg) {
		if (updateMsg.getEntity() instanceof IHumanSubEntity) {
			// handle sub human entity
			IHumanSubEntity humanSubEntity = (IHumanSubEntity) updateMsg.getEntity();
			HumanCache humanCache = this.cache.get(humanSubEntity.getHumanGuid());
			if (humanCache == null) {
				logger.warn(String.format("Human cache is null, the humaGuid is: %d", humanSubEntity.getHumanGuid()));
			}
			// update sub entity
			humanCache.update(humanSubEntity);
		} else {
			logger.warn(String.format("MFucker are you kidding me? DBHumanActor do not handle this class: %s", updateMsg.getEntityClass()
					.getSimpleName()));
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
