package com.stone.db.login;

import java.util.List;

import akka.actor.UntypedActor;

import com.stone.core.db.service.IDBService;
import com.stone.db.entity.HumanEntity;
import com.stone.db.msg.internal.InternalCreateRole;
import com.stone.db.msg.internal.InternalGetRoleList;
import com.stone.db.msg.internal.player.InternalGetRoleListResult;
import com.stone.db.query.DBQueryConstants;

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
		} else if (msg instanceof InternalCreateRole) {
			// do create role things
			InternalCreateRole createRole = (InternalCreateRole) msg;
			HumanEntity humanEntity = new HumanEntity();
			//humanEntity.setGuid(1);
			humanEntity.setLevel(1);
			humanEntity.setName(createRole.getBuilder().getName());
			humanEntity.setPlayerId(createRole.getPlayerId());
			dbService.insert(humanEntity);
			// send role list
			getSelf().forward(new InternalGetRoleList(createRole.getPlayerId()), getContext());
		}
	}
}
