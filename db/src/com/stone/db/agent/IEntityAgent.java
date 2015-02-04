package com.stone.db.agent;

import java.io.Serializable;

import com.stone.core.entity.IEntity;

public interface IEntityAgent<E extends IEntity<?>> {

	public Class<?>[] getBindedClasses();

	public Serializable insert(E entity);

	public void delete(E entity);

	public void update(E entity);

}
