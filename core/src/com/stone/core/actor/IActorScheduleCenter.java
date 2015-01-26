package com.stone.core.actor;

public interface IActorScheduleCenter {

	public IActor dispatch(IActorCallback call, IActorId actorId);

}
