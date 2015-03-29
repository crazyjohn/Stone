package com.stone.core.akka.net;

import java.net.InetSocketAddress;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.io.Tcp.Bound;
import akka.io.Tcp.CommandFailed;
import akka.io.Tcp.Connected;
import akka.io.TcpMessage;

public class Acceptor extends UntypedActor {
	private final ActorRef tcpManager;

	public Acceptor(ActorRef tcpManager) {
		this.tcpManager = tcpManager;
	}
	
	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println("Acceptor received msg: " + msg);
		if (msg instanceof Integer) {
			final int port = (int) msg;
			final InetSocketAddress endpoint = new InetSocketAddress("localhost", port);
			final Object cmd = TcpMessage.bind(getSelf(), endpoint, 100);
			tcpManager.tell(cmd, getSelf());
		} else if (msg instanceof Bound) {
			tcpManager.tell(msg, getSelf());
		} else if (msg instanceof CommandFailed) {
			getContext().stop(getSelf());
		} else if (msg instanceof Connected) {
			final Connected conn = (Connected) msg;
			tcpManager.tell(conn, getSelf());
			final ActorRef handler = getContext().actorOf(Props.create(MyHandler.class));
			getSender().tell(TcpMessage.register(handler), getSelf());
		}
	} 

}
