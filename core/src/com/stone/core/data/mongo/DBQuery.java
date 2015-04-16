package com.stone.core.data.mongo;

import java.util.List;

import com.mongodb.DBObject;
import com.stone.core.entity.IEntity;

public interface DBQuery {

	public Class<? extends IEntity> getEntityClass();

	public String[] getFields();

	public DBObject getQueryObject(String[] paramNames, Object[] values);

	public List<?> processEntityList(List<IEntity> list);
	
	public List<?> processObjectsList(List<Object[]> list);
}