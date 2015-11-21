package com.stone.core.data.config;

import com.stone.core.db.service.orm.HibernateDBService;
import com.stone.core.db.service.orm.IEntityDBService;

/**
 * 访问DB的实现类型的枚举。
 * <p>
 * 
 *
 * @author crazyjohn
 */
public enum DBServiceType {
	HIBERNATE(HibernateDBService.class);

	/**
	 * 判断指定的DB实现类型是否已经有具体实现类型。
	 * 
	 * @param type
	 * @return
	 */
	public static boolean dbTypeNotExist(DBServiceType type) {
		if (type == null)
			return true;
		for (DBServiceType eachType : DBServiceType.values()) {
			if (eachType == type)
				return false;
		}
		return true;
	}

	private Class<? extends IEntityDBService> dbServiceClass;

	private DBServiceType(Class<? extends IEntityDBService> clazz) {
		this.dbServiceClass = clazz;
	}

	/**
	 * 获取访问DB的具体实现类。
	 * 
	 * @return
	 */
	public Class<? extends IEntityDBService> getDBServiceClass() {
		return dbServiceClass;
	}
}
