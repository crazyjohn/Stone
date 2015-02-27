package com.id.test.camel;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class CamelApp {
	public static void main(String[] args)
	{
		ActorSystem system = ActorSystem.create("test");
		Props props = Props.create(MyEndpoint .class);
		ActorRef producer = system.actorOf(props, "MyEndpoint");
//		Camel camel = CamelExtension.get(system);
//		camel.context().addRoutes(new CustomRouteBuilder(responder));
//		camel.context().addRouteDefinition(routeDefinition);
//		producer.tell("<order amount=\"100\" currency=\"PLN\" itemId=\"12345\"/>",
//		    ActorRef.noSender());
	}
}
