package com.stone.core.akka.router;

import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoutedActorRef;
import akka.routing.Router;

public class Master extends UntypedActor {
	Router router;
	{
		List<RoutedActorRef> routees = new ArrayList<RoutedActorRef>();
		for (int i = 0; i < 5; i++) {
			ActorRef r = this.getContext().actorOf(Props.create(Work.class));
			this.getContext().watch(r);
			//routees.add(new RoutedActorRef(r));
		}
		//router = new Router(new Roundrobin);
	}
	@Override
	public void onReceive(Object msg) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
