package com.stone.game.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.actor.BaseActor;
import com.stone.actor.annotation.AskMethod;
import com.stone.actor.annotation.TellMethod;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.id.ActorId;
import com.stone.actor.id.ActorType;
import com.stone.game.GameActorSystem;

/**
 * Just a mock actor;
 * 
 * @author crazyjohn
 *
 */
public class MockActor extends BaseActor {
	private Logger logger = LoggerFactory.getLogger(MockActor.class);

	public static MockActor createMockActor() {
		MockActor actor = new MockActor();
		actor.setActorId(new ActorId(ActorType.MOCK, 0));
		GameActorSystem.getInstance().registerActor(actor);
		return actor;
	}

	@SuppressWarnings("rawtypes")
	@AskMethod
	public IActorFuture testActorMethod(int param1, String param2) {
		return null;
	}

	@TellMethod
	public void testActorTell() {
		logger.info("Call MockActor testActorTell");
	}
}
