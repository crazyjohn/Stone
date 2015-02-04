package com.stone.db.agent;

import java.util.List;

import com.stone.core.entity.IEntity;

public interface ICacheEntityAgent<E extends IEntity<?>, K, V> {

	public List<E> getModifiedEntities(int size);

	public V getFromCache(K id);

	public void setToCache(K id, V cacheObject);
}
