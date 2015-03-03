package com.stone.game.player;

import com.stone.core.msg.MessageParseException;
import com.stone.game.msg.ProtobufMessage;

import akka.actor.ActorRef;

public interface IPlayerModule {

	public Player getPlayer();

	public void onInternalMessage(Object msg, ActorRef playerActor);

	public void onNetMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) throws MessageParseException;
}
