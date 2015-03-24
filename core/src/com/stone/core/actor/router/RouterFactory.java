package com.stone.core.actor.router;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActorContext;
import akka.routing.ActorRefRoutee;
import akka.routing.Routee;
import akka.routing.Router;
import akka.routing.RoutingLogic;

/**
 * Router factory;
 * 
 * @author crazyjohn
 *
 */
public class RouterFactory {

	/**
	 * Create child actor as router's routees;
	 * 
	 * @param context
	 * @param routingLogic
	 * @param actorClass
	 * @param defaultRouteeCount
	 * @param params
	 * @return
	 */
	public static Router createChildActorRouteeRouter(UntypedActorContext context, RoutingLogic routingLogic, Class<?> actorClass,
			int defaultRouteeCount, Object... params) {
		Router router;
		List<Routee> routees = new ArrayList<Routee>();
		for (int i = 0; i < defaultRouteeCount; i++) {
			ActorRef actor = context.actorOf(Props.create(actorClass, params), actorClass.getSimpleName() + i);
			context.watch(actor);
			routees.add(new ActorRefRoutee(actor));
		}
		router = new Router(routingLogic, routees);
		return router;
	}

	/**
	 * Create top level actor as router's routees;
	 * 
	 * @param system
	 * @param routingLogic
	 * @param actorClass
	 * @param defaultRouteeCount
	 * @param params
	 * @return
	 */
	public static Router createTopActorRouteeRouter(ActorSystem system, RoutingLogic routingLogic, Class<?> actorClass, int defaultRouteeCount,
			Object... params) {
		Router router;
		List<Routee> routees = new ArrayList<Routee>();
		for (int i = 0; i < defaultRouteeCount; i++) {
			ActorRef actor = system.actorOf(Props.create(actorClass, params));
			routees.add(new ActorRefRoutee(actor));
		}
		router = new Router(routingLogic, routees);
		return router;
	}

}
