package com.stone.actor.id;

import com.stone.actor.annotation.ImmutableUnit;

/**
 * Actor id;
 * 
 * @author crazyjohn
 *
 */
@ImmutableUnit
public class ActorId implements IActorId {
	private final ActorType actorType;
	private final long id;

	public ActorId(ActorType actorType, long id) {
		this.actorType = actorType;
		this.id = id;
	}

	@Override
	public int getWorkerMonsterIndex(int workerNum) {
		// FIXME: crazyjohn generater a uid to do this thing?
		return (int) (this.id % workerNum);
	}

	@Override
	public ActorType getActorType() {
		return actorType;
	}

	@Override
	public long getId() {
		return id;
	}

}
