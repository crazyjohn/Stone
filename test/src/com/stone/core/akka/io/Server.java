package com.stone.core.akka.io;

import java.net.InetSocketAddress;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.Tcp.Bound;
import akka.io.Tcp.CommandFailed;
import akka.io.Tcp.Connected;
import akka.io.TcpMessage;

public class Server extends UntypedActor {
	final ActorRef manager;

	public Server(ActorRef manager) {
		this.manager = manager;
	}
	
	@Override
	public void preStart() throws Exception {
		final ActorRef tcp = Tcp.get(this.getContext().system()).manager();
		tcp.tell(TcpMessage.bind(getSelf(), new InetSocketAddress("localhost", 8888), 100), getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println("Server received msg: " + msg);
		if (msg instanceof Bound) {
			manager.tell(msg, getSelf());
		} else if (msg instanceof CommandFailed) {
			this.getContext().stop(getSelf());
		} else if (msg instanceof Connected) {
			final Connected conn = (Connected) msg;
			manager.tell(conn, getSelf());
			// ioHandler
			final ActorRef handler = this.getContext().actorOf(Props.create(SimpleIoHandler.class));
			getSender().tell(TcpMessage.register(handler), getSelf());
		}
	}

}
