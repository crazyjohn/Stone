package com.stone.core.actor.router;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActorContext;
import akka.routing.ActorRefRoutee;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.RoutingLogic;

public class RouterFactory {

	public static Router createRouteeActor(UntypedActorContext context, RoutingLogic routingLogic, Class<?> actorClass, int defaultRouteeCount, Object... params) {
		Router router;
		List<Routee> routees = new ArrayList<Routee>();
		for (int i = 0; i < defaultRouteeCount; i++) {
			ActorRef actor = context.actorOf(Props.create(actorClass, params));
			context.watch(actor);
			routees.add(new ActorRefRoutee(actor));
		}
		router = new Router(routingLogic, routees);
		return router;
	}

}
