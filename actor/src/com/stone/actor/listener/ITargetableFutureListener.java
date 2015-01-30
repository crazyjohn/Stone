package com.stone.actor.listener;

import com.stone.actor.id.IActorId;

public interface ITargetableFutureListener<T> extends IActorFutureListener<T> {
	public IActorId getTarget();
}
