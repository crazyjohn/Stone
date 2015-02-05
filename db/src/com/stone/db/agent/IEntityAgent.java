package com.stone.db.agent;

import java.io.Serializable;

import com.stone.core.entity.IEntity;

public interface IEntityAgent {

	public Class<?>[] getBindedClasses();

	public Serializable insert(IEntity<?> entity);

	public void delete(IEntity<?> entity);

	public void update(IEntity<?> entity);

}
