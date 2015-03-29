package com.stone.core.akka.io;

import java.net.InetSocketAddress;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.Tcp.CommandFailed;
import akka.io.Tcp.Connect;
import akka.io.Tcp.ConnectionClosed;
import akka.io.Tcp.Received;
import akka.io.TcpMessage;
import akka.japi.Procedure;
import akka.util.ByteString;

public class Client extends UntypedActor {
	final InetSocketAddress remote;
	final ActorRef listener;

	public Client(InetSocketAddress remote, ActorRef listener) {
		this.remote = remote;
		this.listener = listener;
		// get tcp and connect
		final ActorRef tcp = Tcp.get(this.getContext().system()).manager();
		tcp.tell(TcpMessage.connect(remote), getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println("Client received msg: " + msg);
		if (msg instanceof CommandFailed) {
			// when failed
			listener.tell("failed", getSelf());
			this.getContext().stop(getSelf());
		} else if (msg instanceof Connect) {
			// when connected
			listener.tell(msg, getSelf());
			getSender().tell(TcpMessage.register(getSelf()), getSelf());
			this.getContext().become(connected(getSender()));
		}
	}

	/**
	 * connected;
	 * 
	 * @param connection
	 * @return
	 */
	private Procedure<Object> connected(final ActorRef connection) {
		return new Procedure<Object>() {

			@Override
			public void apply(Object msg) throws Exception {
				if (msg instanceof ByteString) {
					// byte string
					connection.tell(TcpMessage.write((ByteString) msg), getSelf());
				} else if (msg instanceof CommandFailed) {
					// command failed
				} else if (msg instanceof Received) {
					// received data
					listener.tell(((Received) msg).data(), getSelf());
				} else if (msg.equals("close")) {
					// close
					connection.tell(TcpMessage.close(), getSelf());
				} else if (msg instanceof ConnectionClosed) {
					// close connection
					getContext().stop(getSelf());
				}
			}

		};
	}

}
