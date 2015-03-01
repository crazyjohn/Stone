package com.stone.db;

import com.stone.db.login.DBLoginActor;
import com.stone.proto.Auths.Login;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

public class DBMaster extends UntypedActor {
	private ActorRef loginActor;
	private int defaultRouterCounter = 10;

	public DBMaster() {
		loginActor = this.getContext().actorOf(Props.create(DBLoginActor.class).withRouter(new RoundRobinRouter(defaultRouterCounter)));
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Login) {
			loginActor.tell(msg, getSelf());
		}
	}

	public static Props props() {
		return Props.create(DBMaster.class);
	}

}
