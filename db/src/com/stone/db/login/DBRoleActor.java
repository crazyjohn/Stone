package com.stone.db.login;

import java.util.List;

import akka.actor.UntypedActor;

import com.stone.core.db.service.IDBService;
import com.stone.db.entity.HumanEntity;
import com.stone.db.msg.internal.InternalGetRoleList;
import com.stone.db.msg.internal.InternalGetRoleListResult;
import com.stone.db.query.DBQueryConstants;
import com.stone.proto.Auths.CreateRole;

/**
 * DB role actor;
 * <p>
 * Include role create and get role list;
 * 
 * @author crazyjohn
 *
 */
public class DBRoleActor extends UntypedActor {
	private final IDBService dbService;

	public DBRoleActor(IDBService dbService) {
		this.dbService = dbService;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof InternalGetRoleList) {
			// get role list
			InternalGetRoleList roleList = (InternalGetRoleList) msg;
			List<HumanEntity> humanList = dbService.queryByNameAndParams(DBQueryConstants.QUERY_PLAYER_ROLE_BY_PLAYER_ID, new String[] { "playerId" }, new Object[] { roleList.getPlayerId() });
			getSender().tell(new InternalGetRoleListResult(humanList), getSelf());
		} else if (msg instanceof CreateRole.Builder) {
			// do create role things
		}
	}

}
