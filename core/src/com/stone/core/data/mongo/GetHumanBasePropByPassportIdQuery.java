package com.stone.core.data.mongo;

import java.util.List;

import com.mongodb.DBObject;
import com.stone.core.entity.IEntity;

public class GetHumanBasePropByPassportIdQuery extends CommonDBQuery {

	public GetHumanBasePropByPassportIdQuery(
			Class<? extends IEntity> entityClass, String[] fields) {
		super(entityClass, fields);
	}

	@Override
	public DBObject getQueryObject(String[] paramNames, Object[] values) {
		return super.getQueryObject(new String[] { "baseProp.passportId" },
				values);
	}

	@Override
	public List<?> processObjectsList(List<Object[]> list) {
		// for (Object[] arr : list) {
		// HumanBasePropertiesEntity baseProp = new HumanBasePropertiesEntity();
		// MongodbUtil.convert((DBObject) arr[0], baseProp.getBuilder());
		// try {
		// arr[0] = baseProp.toByteArray();
		// } catch (IOException e) {
		// throw new RuntimeException(e);
		// }
		// }
		return list;
	}
}
