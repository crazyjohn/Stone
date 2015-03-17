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
	private static final String TICK = "tick";
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
			// tick
			logger.info("Time to update the dirty human cache.");
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
