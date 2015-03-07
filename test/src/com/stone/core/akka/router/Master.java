package com.stone.core.akka.router;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.routing.ActorRefRoutee;
import akka.routing.RoundRobinRoutingLogic;
import akka.routing.Router;

public class Master extends UntypedActor {
	Router router = new Router(new RoundRobinRoutingLogic());
	{
	
		for (int i = 0; i < 5; i++) {
			ActorRef actor = this.getContext().actorOf(Worker.props("Routee " + i));
			this.getContext().watch(actor);
			router.addRoutee((new ActorRefRoutee(actor)));
		}
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Work) {
			router.route(msg, getSender());
		} else if (msg instanceof Terminated) {
			router = router.removeRoutee(((Terminated)msg).actor());
			ActorRef actor = getContext().actorOf(Props.create(Work.class));
			getContext().watch(actor);
			router = router.addRoutee(new ActorRefRoutee(actor));
		}
	}

}
