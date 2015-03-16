package com.stone.db.cache.holder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.stone.core.entity.IEntity;

/**
 * 持有具有唯一性的实体的持有器; <br>
 * 此类实体只支持更新,且一旦更新以新换旧;
 * 
 * @author crazyjohn
 * 
 * @param <E>
 */
public class ExclusiveEntityHolder<E extends IEntity> extends AbstractEntityHolder<E> {
	protected E entity;
	private List<E> entities = new ArrayList<E>();

	@Override
	public boolean update(E entity) {
		if (this.entity != null) {
			this.isModified = true;
		}
		this.entity = entity;
		return true;
	}

	@Override
	public boolean add(E entity) {
		return this.update(entity);
	}

	@Override
	public Collection<E> getEntities() {
		if (this.entities.isEmpty()) {
			this.entities.add(entity);
			return this.entities;
		}
		if (this.entities.get(0) != this.entity) {
			this.entities.set(0, this.entity);
		}
		return this.entities;
	}

}
