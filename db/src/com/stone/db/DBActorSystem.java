package com.stone.db;

import java.util.Iterator;

import com.stone.actor.future.IActorFuture;
import com.stone.actor.system.ActorSystem;
import com.stone.actor.system.IActorSystemCall;
import com.stone.core.db.service.IDBService;

public class DBActorSystem extends ActorSystem {
	/** dbService */
	private IDBService dbService;

	@Override
	protected boolean handleSystemCall() {
		// 处理系统调用
		boolean noSystemCall = true;
		Iterator<QueuedSytemCall<?>> iterator = this.systemCalls.iterator();
		while (iterator.hasNext()) {
			noSystemCall = false;
			QueuedSytemCall<?> call = iterator.next();
			iterator.remove();
			call.execute();
		}
		return noSystemCall;
	}

	@Override
	protected <T> QueuedSytemCall<T> newQueuedSystemCall(IActorSystemCall<T> call, IActorFuture<T> systemFuture) {
		return new DBQueuedSystemCall<T>(call, systemFuture);
	}

	class DBQueuedSystemCall<T> extends QueuedSytemCall<T> {

		public DBQueuedSystemCall(IActorSystemCall<T> systemCall, IActorFuture<T> future) {
			super(systemCall, future);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void execute() {
			IActorSystemCall<?> call = this.getSystemCall();
			@SuppressWarnings("rawtypes")
			IActorFuture future = this.getFuture();
			future.setResult(call.execute(dbService));
		}

	}
}
