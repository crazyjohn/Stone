package com.i4joy.akka.kok.io;

import java.net.InetSocketAddress;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp.Received;
import akka.util.ByteString;

public class TCPHandler extends UntypedActor {

	final LoggingAdapter log = Logging.getLogger(getContext().system(), getSelf());

	final ActorRef connection;// 连接actor
	final InetSocketAddress remote;// 客户端地址
//	final ActorRef packetBytesBuffer;//ByteString缓冲区
	final ActorRef protoBufDecoder;

	public TCPHandler(ActorRef connection, InetSocketAddress remote) {
		this.connection = connection;
		this.remote = remote;
		getContext().watch(connection);
//		packetBytesBuffer = getContext().actorOf(PacketBytesBuffer.getProps(connection));
		protoBufDecoder = getContext().actorOf(ProtoBufDecoder.getProps(connection));
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Received) {//读数据
			final ByteString data = ((Received) msg).data();
//			packetBytesBuffer.tell(data, getSelf());//读出的数据发给ByteString缓存
			protoBufDecoder.tell(data, getSelf());
		}
		else if (msg instanceof Terminated) {
			System.out.println("TCPHandler terminated!");
			getContext().stop(connection);
			getContext().stop(getSelf());
		}
	}

	@Override
	public void postStop() throws Exception {
		System.out.println("TCPHandler stop!");
		super.postStop();
	}
	
	
}
