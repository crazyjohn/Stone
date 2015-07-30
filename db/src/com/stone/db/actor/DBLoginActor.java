package com.stone.db.actor;

import java.util.List;

import scala.concurrent.Future;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.dispatch.OnSuccess;
import akka.pattern.Patterns;

import com.stone.core.data.uuid.UUIDType;
import com.stone.core.db.service.IDBService;
import com.stone.db.entity.PlayerEntity;
import com.stone.db.msg.internal.player.InternalLoginResult;
import com.stone.db.query.DBQueryConstants;
import com.stone.proto.Auths.Login;

/**
 * The db login actor;
 * 
 * @author crazyjohn
 *
 */
public class DBLoginActor extends UntypedActor {
	private final IDBService dbService;
	private final ActorRef uuidActor;

	public DBLoginActor(IDBService dbService, ActorRef uuidActor) {
		this.dbService = dbService;
		this.uuidActor = uuidActor;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Login.Builder) {
			Login.Builder login = (Login.Builder) msg;
			final List<PlayerEntity> entities = dbService.queryByNameAndParams(DBQueryConstants.QUERY_PLAYER_BY_PUID, new String[] { "puid" },
					new Object[] { login.getPuid() });
			// if not exits this account, just create it
			if (entities == null || entities.size() == 0) {
				final PlayerEntity playerEntity = new PlayerEntity();
				playerEntity.setPuid(login.getPuid());
				Future<?> future = Patterns.ask(uuidActor, UUIDType.PLAYER, 100);
				future.onComplete(new OnSuccess() {
					@Override
					public void onSuccess(Object uuid) throws Throwable {
						playerEntity.setId((long) uuid);
						dbService.insert(playerEntity);
						entities.add(playerEntity);
					}

				}, this.getContext().dispatcher());

			}
			getSender().tell(new InternalLoginResult(entities), getSelf());
		}
	}

}
