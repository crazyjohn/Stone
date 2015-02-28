package com.stone.db.actor;

import java.util.ArrayList;
import java.util.List;

import com.stone.actor.data.IActorDBService;
import com.stone.actor.system.AbstractActorSystemCall;
import com.stone.actor.system.IActorSystem;
import com.stone.core.db.service.IDBService;

/**
 * query call;
 * 
 * @author crazyjohn
 *
 * @param <T>
 */
public class DBSystemQueryCall<T> extends AbstractActorSystemCall<List<T>> {
	private String queryName;
	private String[] params;
	private Object[] values;

	public DBSystemQueryCall(IActorSystem callerSystem, long callerActorId, String queryName, String[] params, Object[] values) {
		super(callerSystem, callerActorId);
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
	public List<T> execute(IActorDBService dbService) {
		// FIXME: crazyjohn ugly code
		IDBService realDBService = null;
		if (dbService instanceof IDBService) {
			realDBService = (IDBService) dbService;
		} else {
			return new ArrayList<T>();
		}
		List<T> result = realDBService.queryByNameAndParams(queryName, params, values);
		return result;
	}

}
