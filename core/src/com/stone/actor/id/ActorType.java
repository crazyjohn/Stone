package com.stone.actor.id;

/**
 * Actor类型;
 * 
 * @author crazyjohn
 *
 */
public enum ActorType {
	PLAYER(1), MANAGER(2);
	private int type;

	ActorType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
