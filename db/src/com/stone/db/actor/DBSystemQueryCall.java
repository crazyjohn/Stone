package com.stone.db.actor;

import java.util.List;

import com.stone.actor.system.IActorSystemCall;
import com.stone.core.db.service.IDBService;

/**
 * query call;
 * 
 * @author crazyjohn
 *
 * @param <T>
 */
public class DBSystemQueryCall<T> implements IActorSystemCall<List<T>> {
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
	public List<T> execute(IDBService dbService) {
		List<T> result = dbService.queryByNameAndParams(queryName, params, values, 1, 0);
		return result;
	}

}
