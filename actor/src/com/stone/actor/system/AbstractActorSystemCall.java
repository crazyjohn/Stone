package com.stone.actor.system;


public abstract class AbstractActorSystemCall<T> implements IActorSystemCall<T> {
	protected IActorSystem callerSystem;
	protected long callerActorId;

	protected AbstractActorSystemCall(IActorSystem callerSystem, long callerActorId) {
		this.callerActorId = callerActorId;
		this.callerSystem = callerSystem;
	}

	@Override
	public IActorSystem getCallerSystem() {
		return callerSystem;
	}

	@Override
	public long getCallerActorId() {
		return callerActorId;
	}

}
