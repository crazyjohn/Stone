package com.stone.db;

import com.stone.core.db.service.IDBService;
import com.stone.db.login.DBLoginActor;
import com.stone.proto.Auths.Login;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

public class DBMaster extends UntypedActor {
	/** login actor */
	private ActorRef loginActor;
	private int defaultRouterCounter = 10;
	/** dbService */
	protected final IDBService dbService;

	public DBMaster(IDBService dbService) {
		this.dbService = dbService;
		loginActor = this.getContext().actorOf(Props.create(DBLoginActor.class, dbService).withRouter(new RoundRobinRouter(defaultRouterCounter)));
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Login.Builder) {
			loginActor.forward(msg, getContext());
		}
	}

	public static Props props(IDBService dbService) {
		return Props.create(DBMaster.class, dbService);
	}

}
