package com.stone.db.cache.holder;

import java.io.Serializable;
import java.util.Collection;

import com.stone.core.entity.IEntity;

/**
 * 抽象的实体持有器;
 * 
 * @author crazyjohn
 * 
 * @param <E>
 */
public abstract class AbstractEntityHolder<E extends IEntity> implements IEntityHolder<E> {
	protected boolean isModified;

	@Override
	public boolean add(E entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean update(E entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void remove(Serializable id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isModified() {
		return this.isModified;
	}

	@Override
	public void resetModified() {
		this.isModified = false;
	}

	@Override
	public void removeAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<E> getEntities() {
		throw new UnsupportedOperationException();
	}

}
