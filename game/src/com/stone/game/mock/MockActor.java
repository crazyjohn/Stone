package com.stone.game.mock;

import com.stone.actor.BaseActor;
import com.stone.actor.IActor;
import com.stone.actor.annotation.ActorMethod;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.id.ActorId;
import com.stone.actor.id.ActorType;

/**
 * Just a mock actor;
 * 
 * @author crazyjohn
 *
 */
public class MockActor extends BaseActor {

	public static IActor createMockActor() {
		MockActor actor = new MockActor();
		actor.setActorId(new ActorId(ActorType.MOCK, 0));
		return actor;
	}

	@SuppressWarnings("rawtypes")
	@ActorMethod
	public IActorFuture testActorMethod(int param1, String param2) {
		return null;
	}
}
