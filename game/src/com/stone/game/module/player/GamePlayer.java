package com.stone.game.module.player;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.google.protobuf.Message.Builder;
import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.server.AGForwardMessage;
import com.stone.core.msg.server.GCMessage;
import com.stone.game.human.Human;
import com.stone.game.player.module.PlayerLoginModule;

/**
 * Game player object;
 * 
 * @author crazyjohn
 *
 */
public class GamePlayer {
	/** binded human */
	private Human human;
	/** binded io session */
	private IoSession session;
	private final long playerId;
	/** player modules */
	private List<IPlayerModule> modules = new ArrayList<IPlayerModule>();

	public GamePlayer(long playerId) {
		this.playerId = playerId;
		// register loginModule
		registerModule(new PlayerLoginModule(this));
	}

	/**
	 * Register the module;
	 * 
	 * @param module
	 */
	public void registerModule(IPlayerModule module) {
		this.modules.add(module);
	}

	/**
	 * Unregister the module;
	 * 
	 * @param module
	 */
	public void unRegisterModule(IPlayerModule module) {
		this.modules.remove(module);
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	public long getPlayerId() {
		return playerId;
	}


	/**
	 * Send message;
	 * 
	 * @param messageType
	 * @param builder
	 */
	public void sendMessage(int messageType, Builder builder) {
		GCMessage message = new GCMessage(messageType, builder, this.playerId, 1, this.session.getRemoteAddress().toString());
		this.session.write(message);
	}

	/**
	 * Handle the system internal message;
	 * 
	 * @param message
	 * @param playerActor
	 */
	public void onInternalMessage(Object message, ActorRef playerActor) {
		// player module
		for (IPlayerModule eachModule : this.modules) {
			eachModule.onInternalMessage(message, playerActor);
		}
		// call human
		if (human == null) {
			return;
		}
		// call human
		this.human.onInternalMessage(message, playerActor);
	}

	/**
	 * Handle the net external messsage;
	 * 
	 * @param msg
	 * @param playerActor
	 * @param dbMaster
	 * @throws MessageParseException
	 */
	public void onExternalMessage(AGForwardMessage msg, ActorRef playerActor, ActorRef dbMaster) throws Exception {
		// player module
		for (IPlayerModule eachModule : this.modules) {
			eachModule.onExternalMessage(msg, playerActor, dbMaster);
		}
		// call human
		if (human == null) {
			return;
		}
		this.human.onExternalMessage(msg, playerActor, dbMaster);
	}

}
