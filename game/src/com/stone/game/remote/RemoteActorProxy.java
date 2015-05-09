package com.stone.game.remote;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;

/**
 * The remote actor proxy, it's means the local actor;
 * 
 * @author crazyjohn
 *
 */
public class RemoteActorProxy extends UntypedActor {
	private ActorSelection remote;

	public RemoteActorProxy(String remotePath) {
		remote = this.getContext().actorSelection(remotePath);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		// send to remote
		remote.forward(msg, this.getContext());
	}

}
