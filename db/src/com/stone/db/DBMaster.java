package com.stone.db;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

import com.stone.core.db.service.IDBService;
import com.stone.db.login.DBLoginActor;
import com.stone.db.login.DBRoleActor;
import com.stone.db.msg.internal.InternalGetRoleList;
import com.stone.proto.Auths.CreateRole;
import com.stone.proto.Auths.Login;
/**
 * The db master actor;
 * 
 * @author crazyjohn
 *
 */
public class DBMaster extends UntypedActor {
	/** login actor */
	private ActorRef loginActor;
	/** role actor */
	private ActorRef roleActor;
	/** default login router count */
	private static final int DEFAULT_ROUTER_COUNT = 10;
	/** dbService */
	protected final IDBService dbService;

	public DBMaster(IDBService dbService) {
		this.dbService = dbService;
		// login actor use router
		loginActor = this.getContext().actorOf(Props.create(DBLoginActor.class, dbService).withRouter(new RoundRobinRouter(DEFAULT_ROUTER_COUNT)));
		// role actor
		roleActor = this.getContext().actorOf(Props.create(DBRoleActor.class, dbService));
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Login.Builder) {
			loginActor.forward(msg, getContext());
		} else if (msg instanceof CreateRole.Builder) {
			roleActor.forward(msg, getContext());
		} else if (msg instanceof InternalGetRoleList) {
			roleActor.forward(msg, getContext());
		}
	}

	public static Props props(IDBService dbService) {
		return Props.create(DBMaster.class, dbService);
	}

}
