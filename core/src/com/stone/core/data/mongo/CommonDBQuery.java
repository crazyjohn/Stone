package com.stone.core.data.mongo;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.stone.core.entity.IEntity;

public class CommonDBQuery implements DBQuery {

	private final Class<? extends IEntity> entityClass;
	private final String[] fields;

	/* (non-Javadoc)
	 * @see com.imop.sgt.core.orm.mongodb.DBQuery#getEntityClass()
	 */
	public Class<? extends IEntity> getEntityClass() {
		return entityClass;
	}

	/* (non-Javadoc)
	 * @see com.imop.sgt.core.orm.mongodb.DBQuery#getFields()
	 */
	public String[] getFields() {
		return fields;
	}

	public CommonDBQuery(Class<? extends IEntity> entityClass, String[] fields) {
		this.entityClass = entityClass;
		this.fields = fields;
	}

	public CommonDBQuery(Class<? extends IEntity> entityClass) {
		this(entityClass, null);
	}

	/* (non-Javadoc)
	 * @see com.imop.sgt.core.orm.mongodb.DBQuery#getQueryObject(java.lang.String[], java.lang.Object[])
	 */
	public DBObject getQueryObject(String[] paramNames, Object[] values) {
		DBObject obj = new BasicDBObject();
		for (int i = 0; i < paramNames.length; i++) {
			obj.put(paramNames[i], values[i]);
		}
		return obj;
	}

	@Override
	public List<?> processEntityList(List<IEntity> list) {
		return list;
	}

	@Override
	public List<?> processObjectsList(List<Object[]> list) {
		return list;
	}

}
