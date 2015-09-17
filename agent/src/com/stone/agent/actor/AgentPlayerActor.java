package com.stone.agent.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.google.protobuf.Message.Builder;
import com.stone.agent.msg.external.CAMessage;
import com.stone.agent.msg.internal.RegisterAgentPlayer;
import com.stone.agent.player.AgentPlayer;
import com.stone.core.msg.server.AGForwardMessage;
import com.stone.core.msg.server.GAForwardMessage;
import com.stone.db.annotation.PlayerInternalMessage;
import com.stone.db.entity.HumanEntity;
import com.stone.db.entity.PlayerEntity;
import com.stone.db.msg.internal.InternalCreateRole;
import com.stone.db.msg.internal.InternalGetRoleList;
import com.stone.db.msg.internal.player.InternalGetRoleListResult;
import com.stone.db.msg.internal.player.InternalLoginResult;
import com.stone.proto.Auths.CreateRole;
import com.stone.proto.Auths.Login;
import com.stone.proto.Auths.LoginResult;
import com.stone.proto.Auths.Role;
import com.stone.proto.Auths.RoleList;
import com.stone.proto.Auths.SelectRole;
import com.stone.proto.MessageTypes.MessageType;

public class AgentPlayerActor extends UntypedActor {
	private final ActorRef dbMaster;
	private final AgentPlayer player;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public AgentPlayerActor(ActorRef dbMaster, AgentPlayer player) {
		this.dbMaster = dbMaster;
		this.player = player;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof CAMessage) {
			// net message use self execute
			CAMessage netMessage = (CAMessage) msg;
			onExternalMessage(netMessage, getSelf());
		} else if (msg.getClass().getAnnotation(PlayerInternalMessage.class) != null) {
			// handle player internal message
			onInternalMessage(msg, getSelf());
		} else if (msg instanceof GAForwardMessage) {
			// handle forward message
			GAForwardMessage forwardMessage = (GAForwardMessage) msg;
			onGAForwardMessage(forwardMessage);
		}else {
			unhandled(msg);
		}
	}

	private void onGAForwardMessage(GAForwardMessage forwardMessage) {
		this.player.sendMessage(forwardMessage);
	}

	private void onInternalMessage(Object msg, ActorRef playerActor) {
		if (msg instanceof InternalLoginResult) {
			InternalLoginResult loginResult = (InternalLoginResult) msg;
			handleLoginResult(loginResult, player, playerActor);
		} else if (msg instanceof InternalGetRoleListResult) {
			InternalGetRoleListResult roleList = (InternalGetRoleListResult) msg;
			handleRoleListResult(roleList, player);
		}
	}

	private void handleRoleListResult(InternalGetRoleListResult roleList, AgentPlayer player) {
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

	private void handleLoginResult(InternalLoginResult loginResult, AgentPlayer player, ActorRef playerActor) {
		if (loginResult.getPlayerEntities().size() > 0) {
			PlayerEntity playerEntity = loginResult.getPlayerEntities().get(0);
			player.setPlayerId(playerEntity.getId());
			// change state
			// if (player.canTransferStateTo(PlayerState.AUTHORIZED)) {
			// player.transferStateTo(PlayerState.AUTHORIZED);
			// }
			logger.info(String.format("Player login, puid: %s", playerEntity.getPuid()));
			// send login result
			player.sendMessage(MessageType.GC_PLAYER_LOGIN_RESULT_VALUE, LoginResult.newBuilder().setSucceed(true));
			// register agent player actor
			this.getContext().parent().tell(new RegisterAgentPlayer(this.player.getPlayerId()), getSelf());
		}

	}

	private void onExternalMessage(CAMessage msg, ActorRef playerActor) throws Exception {
		if (msg.getType() == MessageType.CG_PLAYER_LOGIN_VALUE) {
			final Login.Builder login = msg.getBuilder(Login.newBuilder());
			dbMaster.tell(login, playerActor);
		} else if (msg.getType() == MessageType.CG_GET_ROLE_LIST_VALUE) {
			// get role list
			InternalGetRoleList getRoleList = new InternalGetRoleList(player.getPlayerId());
			dbMaster.tell(getRoleList, playerActor);
		} else if (msg.getType() == MessageType.CG_CREATE_ROLE_VALUE) {
			// create role list
			CreateRole.Builder createRole = msg.getBuilder(CreateRole.newBuilder());
			dbMaster.tell(new InternalCreateRole(player.getPlayerId(), createRole), playerActor);
		} else if (msg.getType() == MessageType.CG_SELECT_ROLE_VALUE) {
			// select role
			SelectRole.Builder selectRole = msg.getBuilder(SelectRole.newBuilder());
			// FIXME: crazyjohn forward this msg to game server, first get the
			// sceneId
			int sceneId = 1;
			forwardToGameServer(MessageType.CG_SELECT_ROLE_VALUE, selectRole, sceneId);
		}
	}

	protected void forwardToGameServer(int messageType, Builder builder, int sceneId) {
		// FIXME: crazyjohn forward this msg to game server
		// playerId; playerIp; sceneId; msg;
		AGForwardMessage forwardMsg = new AGForwardMessage(messageType, builder, this.player.getPlayerId(), sceneId, this.player.getClientIp());
		this.getContext().parent().tell(forwardMsg, getSelf());
	}

}
