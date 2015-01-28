package com.stone.actor.listener;

import com.stone.actor.future.IActorFuture;

public interface IActorFutureListener<T> {

	public void onComplete(IActorFuture<T> future);

}
