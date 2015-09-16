package com.stone.game.player.module;

import akka.actor.ActorRef;

import com.stone.core.data.msg.DBGetMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.ProtobufMessage;
import com.stone.db.entity.HumanEntity;
import com.stone.db.msg.internal.InternalCreateRole;
import com.stone.db.msg.internal.InternalGetRoleList;
import com.stone.db.msg.internal.player.InternalSelectRoleResult;
import com.stone.game.module.player.BasePlayerModule;
import com.stone.game.module.player.Player;
import com.stone.proto.Auths.CreateRole;
import com.stone.proto.Auths.EnterScene;
import com.stone.proto.Auths.Login;
import com.stone.proto.Auths.SelectRole;
import com.stone.proto.Humans.Human;
import com.stone.proto.MessageTypes.MessageType;

/**
 * The player's login module;
 * 
 * @author crazyjohn
 *
 */

public class PlayerNewLoginModule extends BasePlayerModule {

	public PlayerNewLoginModule(Player player) {
		super(player);
	}

	@Override
	public void onInternalMessage(Object msg, ActorRef playerActor) {
		if (msg instanceof InternalSelectRoleResult) {
			// select role result
			// send enter scene
			Human.Builder humanBuilder = Human.newBuilder();
			// bind player and human for each other
			com.stone.game.human.Human human = new com.stone.game.human.Human(player);
			player.setHuman(human);
			HumanEntity humanEntity = ((InternalSelectRoleResult) msg).getEntity();
			// load
			human.onLoad(humanEntity);
			humanBuilder.setGuid(humanEntity.getGuid()).setLevel(humanEntity.getLevel()).setName(humanEntity.getName())
					.setPlayerId(humanEntity.getPlayerId());
			EnterScene.Builder enterScene = EnterScene.newBuilder();
			enterScene.setHuman(humanBuilder);
			player.sendMessage(MessageType.GC_ENTER_SCENE_VALUE, enterScene);
		}
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) throws MessageParseException {
		if (msg.getType() == MessageType.CG_PLAYER_LOGIN_VALUE) {
			final Login.Builder login = msg.parseBuilder(Login.newBuilder());
			dbMaster.tell(login, playerActor);
		} else if (msg.getType() == MessageType.CG_GET_ROLE_LIST_VALUE) {
			// get role list
			InternalGetRoleList getRoleList = new InternalGetRoleList(player.getPlayerId());
			dbMaster.tell(getRoleList, playerActor);
		} else if (msg.getType() == MessageType.CG_CREATE_ROLE_VALUE) {
			// create role list
			CreateRole.Builder createRole = msg.parseBuilder(CreateRole.newBuilder());
			dbMaster.tell(new InternalCreateRole(player.getPlayerId(), createRole), playerActor);
		} else if (msg.getType() == MessageType.CG_SELECT_ROLE_VALUE) {
			// select role
			SelectRole.Builder selectRole = msg.parseBuilder(SelectRole.newBuilder());
			dbMaster.tell(new DBGetMessage(selectRole.getRoleId(), HumanEntity.class), playerActor);
		}
	}

}
