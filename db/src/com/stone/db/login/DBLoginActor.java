package com.stone.db.login;

import java.util.List;

import akka.actor.UntypedActor;

import com.stone.core.db.service.IDBService;
import com.stone.db.entity.PlayerEntity;
import com.stone.db.msg.internal.player.InternalLoginResult;
import com.stone.db.query.DBQueryConstants;
import com.stone.proto.Auths.Login;

public class DBLoginActor extends UntypedActor {
	private final IDBService dbService;

	public DBLoginActor(IDBService dbService) {
		this.dbService = dbService;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Login.Builder) {
			Login.Builder login = (Login.Builder) msg;
			List<PlayerEntity> entities = dbService.queryByNameAndParams(DBQueryConstants.QUERY_PLAYER_BY_NAME_AND_PASSWORD, new String[] { "userName", "password" },
					new Object[] { login.getUserName(), login.getPassword() });
			getSender().tell(new InternalLoginResult(entities), getSelf());
		}
	}

}
