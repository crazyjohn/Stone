package com.stone.db.service;

import java.net.URL;
import java.util.Properties;

import com.stone.core.db.service.IDBService;

/**
 * 根据DBConfiguration创建DBService的实例。
 * 
 * @see DBConfiguration
 * 
 * @author crazyjohn
 */
public class DBServiceFactory {

	public static IDBService createDBService(DBConfiguration dbConfig) {
		if (dbConfig == null) {
			throw new IllegalArgumentException("DBConfiguration can not be null.");
		}
		if (DBServiceType.dbTypeNotExist(dbConfig.dbServiceType)) {
			throw new IllegalArgumentException("Not dao build type defined.");
		}
		try {
			return dbConfig.dbServiceType.getDBServiceClass().getConstructor(URL.class, Properties.class).newInstance(dbConfig.dbConfigUrl, dbConfig.dbProperties);
		} catch (Exception e) {
			throw new RuntimeException("Can not initialize the DBService implementation!", e);
		}
	}
}
