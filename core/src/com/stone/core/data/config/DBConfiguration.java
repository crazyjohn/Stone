package com.stone.core.data.config;


import java.net.URL;
import java.util.Properties;

/**
 * DataBase连接的配置类。<br>
 * 
 * @author crazyjohn
 */
public class DBConfiguration {

	public final DBServiceType dbServiceType;
	public final URL dbConfigUrl;
	public final Properties dbProperties;

	/**
	 * 构造函数
	 * 
	 * @param dbServiceType 使用的DBServie类型, {@link DBServiceType}
	 * @param dbConfigFileName	DBService在初始化时所需的配置文件的名称
	 * @param dbProperties	初始化DBService时用到的一些属性
	 */
	public DBConfiguration(String dbServiceType, String dbConfigFileName,
			Properties dbProperties) {
		try {
			this.dbServiceType = DBServiceType.valueOf(dbServiceType.toUpperCase());
		} catch (Exception e) {
			StringBuilder sb = new StringBuilder();
			for (DBServiceType dbst : DBServiceType.values()) {
				sb.append(dbst.toString());
				sb.append(" | ");
			}
			String allowedTypes = sb.toString();
			throw new IllegalArgumentException("\nDBServiceType must be one of(No Case Insensitive): "
					+ allowedTypes.substring(0, allowedTypes.length() - 3));
		}
		if (dbConfigFileName != null) {
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			URL url = classLoader.getResource(dbConfigFileName);
			this.dbConfigUrl = url;
		} else {
			this.dbConfigUrl = null;
		}
		this.dbProperties = dbProperties;
	}
}
