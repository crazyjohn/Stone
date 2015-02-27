package com.i4joy.akka.kok.protocol;

import java.io.Serializable;

public class PCreateDBDateSource implements Serializable {

	private final String user;
	private final String password;
	private final String jdbcUrl;
	private final String dirver;
	private final int initialPoolSize;
	private final int minPoolSize;
	private final int maxPoolSize;
	private final int maxStatements;
	private final int maxIdleTime;
	private final String DBName;
	

	// dataSource.setPassword(json.getString("Password"));
	// dataSource.setUser(json.getString("User"));
	// dataSource.setJdbcUrl(json.getString("JdbcUrl"));
	// dataSource.setDriverClass(json.getString("Driver"));
	// dataSource.setInitialPoolSize(json.getInt("InitialPoolSize"));
	// dataSource.setMinPoolSize(json.getInt("MinPoolSize"));
	// dataSource.setMaxPoolSize(json.getInt("MaxPoolSize"));
	// dataSource.setMaxStatements(json.getInt("MaxStatements"));
	// dataSource.setMaxIdleTime(json.getInt("MaxIdleTime"));
	// db_hm.put(json.getString("DBName"), dataSource);

	public PCreateDBDateSource(String user, String password, String jdbcUrl, String dirver, int initialPoolSize, int minPoolSize, int maxPoolSize,
			int maxStatements, int maxIdleTime, String DBName) {
		this.user = user;
		this.password = password;
		this.jdbcUrl = jdbcUrl;
		this.dirver = dirver;
		this.initialPoolSize = initialPoolSize;
		this.minPoolSize = minPoolSize;
		this.maxPoolSize = maxPoolSize;
		this.maxStatements = maxStatements;
		this.maxIdleTime = maxIdleTime;
		this.DBName = DBName;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getJdbcUrl() {
		return jdbcUrl;
	}

	public String getDirver() {
		return dirver;
	}

	public int getInitialPoolSize() {
		return initialPoolSize;
	}

	public int getMinPoolSize() {
		return minPoolSize;
	}

	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	public int getMaxStatements() {
		return maxStatements;
	}

	public int getMaxIdleTime() {
		return maxIdleTime;
	}

	public String getDBName() {
		return DBName;
	}
}
