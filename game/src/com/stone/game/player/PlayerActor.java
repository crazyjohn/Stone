package com.stone.game.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.db.entity.PlayerEntity;
import com.stone.db.msg.system.SystemLoginResult;
import com.stone.game.msg.GameSessionCloseMessage;
import com.stone.game.msg.GameSessionOpenMessage;
import com.stone.game.msg.ProtobufMessage;
import com.stone.proto.Auths.LoginResult;
import com.stone.proto.MessageTypes.MessageType;

/**
 * The palyer actor;
 * 
 * @author crazyjohn
 *
 */
public class PlayerActor extends UntypedActor {
	/** real player */
	protected final Player player;
	/** db master */
	protected ActorRef dbMaster;
	/** logger */
	private Logger logger = LoggerFactory.getLogger(PlayerActor.class);

	public PlayerActor(Player player) {
		this.player = player;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof ProtobufMessage) {
			// net message use self execute
			ProtobufMessage netMessage = (ProtobufMessage) msg;
			netMessage.execute();
		} else if (msg instanceof SystemLoginResult) {
			SystemLoginResult loginResult = (SystemLoginResult) msg;
			handleLoginResult(loginResult);
		} else if (msg instanceof GameSessionOpenMessage) {
			// open session
			GameSessionOpenMessage sessionOpen = (GameSessionOpenMessage) msg;
			sessionOpen.execute();
		} else if (msg instanceof GameSessionCloseMessage) {
			// close session
			GameSessionCloseMessage sessionClose = (GameSessionCloseMessage) msg;
			sessionClose.execute();
		} else {
			// unhandle msg
			unhandled(msg);
		}
	}

	/**
	 * Handle the login result;
	 * 
	 * @param loginResult
	 */
	private void handleLoginResult(SystemLoginResult loginResult) {
		if (loginResult.getPlayerEntities().size() > 0) {
			PlayerEntity playerEntity = loginResult.getPlayerEntities().get(0);
			player.setPlayerId(playerEntity.getId());
			// change state
			// if (player.canTransferStateTo(PlayerState.AUTHORIZED)) {
			// player.transferStateTo(PlayerState.AUTHORIZED);
			// }
			logger.info(String.format("Player login, userName: %s", playerEntity.getUserName()));
			// send login result
			player.sendMessage(MessageType.GC_PLAYER_LOGIN_RESULT_VALUE, LoginResult.newBuilder().setSucceed(true));
		}
	}

	public static Props props() {
		Player player = new Player();
		// if (player.canTransferStateTo(PlayerState.CONNECTED)) {
		// player.transferStateTo(PlayerState.CONNECTED);
		// }
		return Props.create(PlayerActor.class, player);
	}

}
