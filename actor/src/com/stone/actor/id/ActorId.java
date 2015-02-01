package com.stone.actor.id;

public class ActorId implements IActorId {
	private ActorType actorType;
	private long id;

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
