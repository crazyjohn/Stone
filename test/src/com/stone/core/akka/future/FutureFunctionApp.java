package com.stone.core.akka.future;

import java.util.concurrent.Callable;

import scala.concurrent.Future;
import akka.actor.ActorSystem;
import akka.dispatch.Mapper;
import static akka.dispatch.Futures.*;

/**
 * The scala functional future;
 * 
 * @author crazyjohn
 *
 */
public class FutureFunctionApp {

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("MySystem");
		Future<String> f1 = future(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "Hello World";
			}
		}, system.dispatcher());

		Future<Integer> f2 = f1.map(new Mapper<String, Integer>() {
			public Integer apply(String s) {
				return s.length();
			}
		}, system.dispatcher());

		f2.onSuccess(new FutureApp.PrintResult<Integer>(), system.dispatcher());
	}

}
