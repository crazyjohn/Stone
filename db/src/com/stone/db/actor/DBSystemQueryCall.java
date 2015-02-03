package com.stone.db.actor;

import com.stone.actor.system.IActorSystemCall;
import com.stone.core.db.service.IDBService;

/**
 * query call;
 * 
 * @author crazyjohn
 *
 * @param <T>
 */
public class DBSystemQueryCall<T> implements IActorSystemCall<T> {
	private String queryName;
	private String[] params;
	private Object[] values;

	public DBSystemQueryCall(String queryName, String[] params, Object[] values) {
		this.queryName = queryName;
		this.params = params;
		this.values = values;
	}

	public String getQueryName() {
		return queryName;
	}

	public String[] getParams() {
		return params;
	}

	public Object[] getValues() {
		return values;
	}

	@Override
	public T execute(IDBService dbService) {
		// TODO Auto-generated method stub
		return null;
	}

}
