package com.stone.game.msg;

import akka.actor.ActorRef;

import com.stone.core.msg.BaseMessage;
import com.stone.core.session.GamePlayerSession;

/**
 * 基础CG消息;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseCGMessage extends BaseMessage implements CGMessage {
	protected GamePlayerSession session;

	@Override
	public GamePlayerSession getSession() {
		return session;
	}

	@Override
	public void setSession(GamePlayerSession session) {
		this.session = session;
	}

	@Override
	public ActorRef getPlayerActor() {
		return session.getPlayerActor();
	}

}
