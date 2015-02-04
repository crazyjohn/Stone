package com.stone.db.agent;

import java.io.Serializable;
import java.util.List;

import com.stone.core.entity.IEntity;

public interface IDBAgent {

	public <T> List<T> query(String queryName, String[] params, Object[] values);

	public Serializable insert(IEntity<?> entity);

	public void delete(IEntity<?> entity);

	public void update(IEntity<?> entity);

	public IEntity<?> get(long id, Class<?> entityClass);
}
