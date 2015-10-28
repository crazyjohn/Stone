package com.stone.core.data.cache.holder;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.stone.core.entity.IEntity;

/**
 * 以集合的形式保存实体的持有器类型;
 * 
 * @author crazyjohn
 * 
 */
public class CollectionEntityHolder<E extends IEntity> extends AbstractEntityHolder<E> {
	private Map<Serializable, E> entities = new HashMap<Serializable, E>();

	@Override
	public boolean add(E entity) {
		IEntity result = entities.put(entity.getId(), entity);
		this.isModified = true;
		return result != null;
	}

	@Override
	public boolean update(E entity) {
		E result = this.entities.put(entity.getId(), entity);
		this.isModified = true;
		return result != null;
	}

	@Override
	public void remove(Serializable id) {
		this.entities.remove(id);
		this.isModified = true;
	}

	@Override
	public void removeAll() {
		this.entities.clear();
		this.isModified = true;
	}

	@Override
	public Collection<E> getEntities() {
		return Collections.unmodifiableCollection(this.entities.values());
	}

}
