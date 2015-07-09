package com.stone.core.akka.compute;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinRouter;

@SuppressWarnings("deprecation")
public class MasterActor extends UntypedActor {
	ActorRef mapActor;
	ActorRef reduceActor;
	ActorRef aggregateActor;

	public MasterActor() {
		this.mapActor = this.getContext().actorOf(Props.create(MapActor.class).withRouter(new RoundRobinRouter(5)), "map");
		this.reduceActor = this.getContext().actorOf(Props.create(ReduceActor.class).withRouter(new RoundRobinRouter(5)), "reduce");
		this.aggregateActor = this.getContext().actorOf(Props.create(AggregateActor.class).withRouter(new RoundRobinRouter(5)), "aggregate");
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			mapActor.tell(message, getSelf());
		} else if (message instanceof MapData) {
			reduceActor.tell(message, getSelf());
		} else if (message instanceof ReduceData) {
			aggregateActor.tell(message, getSelf());
		} else if (message instanceof Result) {
			aggregateActor.forward(message, getContext());
		} else {
			unhandled(message);
		}
	} 

}
