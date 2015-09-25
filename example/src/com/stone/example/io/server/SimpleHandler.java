package com.stone.example.io.server;

import akka.actor.UntypedActor;
import akka.io.Tcp.ConnectionClosed;
import akka.io.Tcp.Received;
import akka.util.ByteString;

public class SimpleHandler extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Received) {
			final ByteString data = ((Received) msg).data();
			System.out.println(data);
		} else if (msg instanceof ConnectionClosed) {
			getContext().stop(getSelf());
		}
	}

}
