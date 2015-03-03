package com.stone.game.player;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.db.entity.HumanEntity;
import com.stone.db.entity.PlayerEntity;
import com.stone.db.msg.internal.InternalGetRoleListResult;
import com.stone.db.msg.internal.InternalLoginResult;
import com.stone.game.msg.GameSessionCloseMessage;
import com.stone.game.msg.GameSessionOpenMessage;
import com.stone.game.msg.ProtobufMessage;
import com.stone.game.msg.handler.MessageHandlerRegistry;
import com.stone.proto.Auths.LoginResult;
import com.stone.proto.Auths.Role;
import com.stone.proto.Auths.RoleList;
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
			MessageHandlerRegistry.handle(netMessage, player);
		} else if (msg instanceof InternalLoginResult) {
			InternalLoginResult loginResult = (InternalLoginResult) msg;
			handleLoginResult(loginResult);
		} else if (msg instanceof InternalGetRoleListResult) {
			InternalGetRoleListResult roleList = (InternalGetRoleListResult) msg;
			handleRoleListResult(roleList);
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
	 * Handle the role list;
	 * 
	 * @param roleList
	 */
	private void handleRoleListResult(InternalGetRoleListResult roleList) {
		if (roleList.getHumanEntities().size() <= 0) {
			player.sendMessage(MessageType.GC_GET_ROLE_LIST_VALUE, RoleList.newBuilder());
		} else {
			RoleList.Builder roleListBuilder = RoleList.newBuilder();
			for (HumanEntity eachHuman : roleList.getHumanEntities()) {
				roleListBuilder.addRoleList(Role.newBuilder().setRoleId(eachHuman.getGuid()).setName(eachHuman.getName()));
			}
			player.sendMessage(MessageType.GC_GET_ROLE_LIST_VALUE, roleListBuilder);
		}
	}

	/**
	 * Handle the login result;
	 * 
	 * @param loginResult
	 */
	private void handleLoginResult(InternalLoginResult loginResult) {
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

	public static Props props(IoSession session) {
		Player player = new Player();
		player.setSession(session);
		// if (player.canTransferStateTo(PlayerState.CONNECTED)) {
		// player.transferStateTo(PlayerState.CONNECTED);
		// }
		return Props.create(PlayerActor.class, player);
	}

}
