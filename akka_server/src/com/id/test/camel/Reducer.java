package com.id.test.camel;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.camel.CamelMessage;

public class Reducer extends UntypedActor {
	
	public static Props getProps()
	{
		return Props.create(Reducer.class);
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		if(message instanceof CamelMessage)
		{
			getSender().tell(String.format("Received message: %s"," from MyEndpoint "), getSelf());
		}
		
	}

}
