package com.i4joy.akka.kok.db;

import java.util.HashMap;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.protocol.PCreateDBDateSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBManager extends UntypedActor {
	private HashMap<String, ActorRef> db_hm = new HashMap<String, ActorRef>();// DBDataSource表

	public static Props props() {
		return Props.create(DBManager.class);
	}

	// 添加一个新的DBDataSource
	public void addDBResource(PCreateDBDateSource pcdd) {
		if (!db_hm.containsKey(pcdd.getDBName())) {
			try {
				ComboPooledDataSource dataSource = new ComboPooledDataSource();
				dataSource.setPassword(pcdd.getPassword());
				dataSource.setUser(pcdd.getUser());
				dataSource.setJdbcUrl(pcdd.getJdbcUrl());
				dataSource.setDriverClass(pcdd.getDirver());
				dataSource.setInitialPoolSize(pcdd.getInitialPoolSize());
				dataSource.setMinPoolSize(pcdd.getMinPoolSize());
				dataSource.setMaxPoolSize(pcdd.getMaxPoolSize());
				dataSource.setMaxStatements(pcdd.getMaxStatements());
				dataSource.setMaxIdleTime(pcdd.getMaxIdleTime());
				if(pcdd.getDBName().equals(TextProperties.getText("DB_NAME_CONFIG")))
				{
					db_hm.put(pcdd.getDBName(), getContext().actorOf(DBDataSourceConfig.props(dataSource, pcdd.getDBName())));	
				}else if(pcdd.getDBName().startsWith(TextProperties.getText("DB_NAME_USER")))
				{
					db_hm.put(pcdd.getDBName(), getContext().actorOf(DBDataSourceUser.props(dataSource, pcdd.getDBName())));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof PCreateDBDateSource) {// 收到创建DBDataSource通知
			PCreateDBDateSource pcdd = (PCreateDBDateSource) msg;
			addDBResource(pcdd);
		}
	}
}
