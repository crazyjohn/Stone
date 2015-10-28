package com.stone.db.entity.converter;

import com.stone.core.converter.IConverter;
import com.stone.db.cache.HumanCache;
import com.stone.db.entity.HumanEntity;
import com.stone.db.entity.HumanItemEntity;
import com.stone.proto.entity.Entities.HumanItemData;

/**
 * Converter;
 * 
 * @author crazyjohn
 *
 */
public class HumanConverter implements IConverter<HumanEntity, HumanCache> {

	@Override
	public HumanCache convertFrom(HumanEntity entity) {
		HumanCache cache = new HumanCache();
		cache.setHumanGuid(entity.getGuid());
		cache.setName(entity.getName());
		cache.setLevel(entity.getLevel());
		cache.setPlayerId(entity.getPlayerId());
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
		entity.setPlayerId(toObject.getPlayerId());
		entity.setName(toObject.getName());
		entity.setLevel(toObject.getLevel());
		// item
		for (HumanItemEntity itemEntity : toObject.getEntites(HumanItemEntity.class)) {
			entity.getBuilder().addHumanItems(itemEntity.getBuilder().clone());
		}
		return entity;
	}

}
