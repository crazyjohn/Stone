package com.stone.core.akka.compute;


import scala.concurrent.Await;
import scala.concurrent.Future;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
public class MapReduceApp {

	public static void main(String[] args) throws Exception {
		Timeout timeout = new Timeout(60 * 1000);
		ActorSystem system = ActorSystem.create("MapReduceApp");
		ActorRef master = system.actorOf(Props.create(MasterActor.class));
		master.tell("the quick brown fox tried to jump over the lazy dog and fell on the dog", null);
		master.tell("dog is man's best friend", null);
		master.tell("dog and fox belong to the same family", null);
		Thread.sleep(10 * 1000);
		Future<Object> future = Patterns.ask(master, new Result(), timeout);
		String result = (String)Await.result(future, timeout.duration());
		System.out.println(result);
		system.shutdown();
	}

}
