package com.stone.actor.system;

import com.stone.actor.id.IActorId;

public abstract class AbstractActorSystemCall<T> implements IActorSystemCall<T> {
	protected IActorSystem callerSystem;
	protected IActorId callerActorId;

	protected AbstractActorSystemCall(IActorSystem callerSystem, IActorId callerActorId) {
		this.callerActorId = callerActorId;
		this.callerSystem = callerSystem;
	}

	@Override
	public IActorSystem getCallerSystem() {
		return callerSystem;
	}

	@Override
	public IActorId getCallerActorId() {
		return callerActorId;
	}

}
