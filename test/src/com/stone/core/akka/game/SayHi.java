package com.stone.core.akka.game;

import akka.actor.ActorRef;

/**
 * say hi to an target;
 * 
 * @author crazyjohn
 *
 */
public class SayHi {
	private final ActorRef target;

	public SayHi(ActorRef target) {
		this.target = target;
	}

	public ActorRef getTarget() {
		return target;
	}

}
