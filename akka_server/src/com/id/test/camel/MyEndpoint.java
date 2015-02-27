package com.id.test.camel;

import java.util.Map;

import akka.actor.ActorRef;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;
import akka.routing.RoundRobinRouter;

public class MyEndpoint extends UntypedConsumerActor{
	  private String uri;
	 
	  public String getEndpointUri() {
	    return uri;
	  }
	  
	  private final ActorRef routers;
	  
	 
	  public void onReceive(Object message) throws Exception {
	    if (message instanceof CamelMessage) {
	    	CamelMessage camelMessage = (CamelMessage) message;
	    	Map map = camelMessage.getHeaders();
	    	
	    	routers.tell(camelMessage, getSender());
//	    	String body = camelMessage.getBodyAs(String.class, getCamelContext());
//	    	camelMessage.body();
//	    	getSender().tell(String.format("Received message: %s"," from MyEndpoint "), getSelf());
	    } else
	      unhandled(message);
	  }
	 
	  // Extra constructor to change the default uri,
	  // for instance to "jetty:http://localhost:8877/example"
	  public MyEndpoint(String uri) {
	    this.uri = uri;
	    routers = getContext().actorOf(Reducer.getProps());
	  }
	 
	  public MyEndpoint() {
		  //?matchOnUriPrefix=true&enableMultipartFilter=false&useContinuation=false
	    this.uri = "jetty:http://192.168.0.118:8877/example";
	    routers = getContext().actorOf(Reducer.getProps().withRouter(new RoundRobinRouter(5)));
	  }
	}