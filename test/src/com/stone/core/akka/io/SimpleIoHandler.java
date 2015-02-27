package com.stone.core.akka.io;

import akka.actor.UntypedActor;
import akka.io.Tcp.ConnectionClosed;
import akka.io.Tcp.Received;
import akka.util.ByteString;

public class SimpleIoHandler extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Received) {
			final ByteString data = ((Received) msg).data();
			System.out.println(data);
		} else if (msg instanceof ConnectionClosed) {
			this.getContext().stop(getSelf());
		}
	}

}
