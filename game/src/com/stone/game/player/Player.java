package com.stone.game.player;

import org.apache.mina.core.session.IoSession;

import akka.actor.ActorRef;

import com.google.protobuf.Message.Builder;
import com.stone.core.msg.MessageParseException;
import com.stone.core.state.IState;
import com.stone.core.state.IStateManager;
import com.stone.game.human.Human;
import com.stone.game.msg.ProtobufMessage;
import com.stone.game.player.module.PlayerLoginModule;
import com.stone.game.player.state.PlayerState;

/**
 * Game player object;
 * 
 * @author crazyjohn
 *
 */
public class Player implements IStateManager {

	/** binded human */
	private Human human;
	/** binded io session */
	private IoSession session;
	/** current state */
	private IState currentState = PlayerState.NONE;
	private long playerId;

	/** item module */
	private IPlayerModule loginModule;

	public Player() {
		// loginModule
		loginModule = new PlayerLoginModule(this);
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public IoSession getSession() {
		return session;
	}

	public void setSession(IoSession session) {
		this.session = session;
	}

	@Override
	public IState getCurrentState() {
		return currentState;
	}

	@Override
	public IState setCurrentState(IState state) {
		currentState = state;
		return currentState;
	}

	@Override
	public boolean canTransferStateTo(IState state) {
		return currentState.canTransferTo(state);
	}

	@Override
	public boolean transferStateTo(IState state) {
		if (!canTransferStateTo(state)) {
			return false;
		}
		setCurrentState(state);
		return true;
	}

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	/**
	 * Send message;
	 * 
	 * @param messageType
	 * @param builder
	 */
	public void sendMessage(int messageType, Builder builder) {
		ProtobufMessage message = new ProtobufMessage(messageType);
		message.setBuilder(builder);
		this.session.write(message);
	}

	/**
	 * Handle the system internal message;
	 * 
	 * @param message
	 * @param playerActor
	 */
	public void onInternalMessage(Object message, ActorRef playerActor) {
		loginModule.onInternalMessage(message, playerActor);
		if (human == null) {
			return;
		}
		// call human
		this.human.onMessage(message, playerActor);
	}

	/**
	 * Handle the net external messsage;
	 * 
	 * @param msg
	 * @param playerActor
	 * @param dbMaster
	 * @throws MessageParseException
	 */
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) throws MessageParseException {
		loginModule.onExternalMessage(msg, playerActor, dbMaster);
	}

}
