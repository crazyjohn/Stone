package com.stone.game.session;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.stone.core.session.BaseSession;

/**
 * 游戏玩家回话信息;
 * 
 * @author crazyjohn
 *
 */
public class GamePlayerSession extends BaseSession implements IPlayerSession {
	protected ActorRef playerActor;

	public GamePlayerSession(IoSession session) {
		super(session);
	}
	
	public void setPlayerActor(ActorRef playerActor) {
		this.playerActor = playerActor;
	}

	@Override
	public ActorRef getPlayerActor() {
		return playerActor;
	}

	@Override
	public String toString() {
		return session.toString();
	}

}
