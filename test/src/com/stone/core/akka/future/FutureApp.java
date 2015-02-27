package com.stone.core.akka.future;

import static akka.dispatch.Futures.future;

import java.util.concurrent.Callable;

import scala.concurrent.Future;
import scala.concurrent.Promise;
import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import akka.dispatch.OnSuccess;

public class FutureApp {
	
	public final static class PrintResult<T> extends OnSuccess<T> {

		@Override
		public void onSuccess(T t) throws Throwable {
			System.out.println(t);
		}
		
	}

	public static void main(String[] args) {
		// create system
		ActorSystem system = ActorSystem.create("MySystem");
		Future<String> future = future(new Callable<String>() {

			@Override
			public String call() throws Exception {
				return "Hello Callable";
			}
			
		}, system.dispatcher());
		// onSuccess
		future.onSuccess(new PrintResult<String>(), system.dispatcher());
		
		// promise
		Promise<String> promise = Futures.promise();
		Future<String> theFuture = promise.future();
		theFuture.onSuccess(new PrintResult<String>(), system.dispatcher());
		promise.success("Hello Promise");
	}

}
