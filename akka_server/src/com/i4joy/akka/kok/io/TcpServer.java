package com.i4joy.akka.kok.io;

import java.net.InetSocketAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.io.Tcp;
import akka.io.Tcp.Bind;
import akka.io.Tcp.Bound;
import akka.io.Tcp.CommandFailed;
import akka.io.Tcp.Connected;
import akka.io.TcpMessage;

import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_isAllReady;
import com.i4joy.util.Tools;

public class TcpServer extends UntypedActor {
	public static final String bindIp = TextProperties.getText("IOIP");
	public static boolean isAllReady = false;
	protected final Log logger = LogFactory.getLog(getClass());
	private final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();// 集群代理
	final LoggingAdapter log = Logging.getLogger(getContext().system(), getSelf());
	public static final String topic = "TcpServer";
	@Override
	public SupervisorStrategy supervisorStrategy() {
		return SupervisorStrategy.stoppingStrategy();
	}

	@Override
	public void preStart() throws Exception {
		final ActorRef tcpManager = Tcp.get(getContext().system()).manager();// 获得tcpManager
		tcpManager.tell(TcpMessage.bind(getSelf(), new InetSocketAddress(bindIp, 8000), 100), getSelf());// 启动tcp服务器
		mediator.tell(new DistributedPubSubMediator.Subscribe(topic, getSelf()), getSelf());
	}

	@Override
	public void postRestart(Throwable msg) throws Exception {
		getContext().stop(getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Bound) {

		} else if (msg instanceof CommandFailed) {// 连接失败
			final CommandFailed failed = (CommandFailed) msg;
			if (failed.cmd() instanceof Bind) {// 绑定IP失败
				getContext().stop(getSelf());// 关闭
			} else {

			}
		} else if (msg instanceof Connected) {// 已经连接上了
			final Connected conn = (Connected) msg;
			final ActorRef connection = getSender();
			if (isAllReady) {
				final ActorRef handler = getContext().actorOf(Props.create(TCPHandler.class, connection, conn.remoteAddress()));// 创建handler
				connection.tell(TcpMessage.register(handler, true, true), getSelf());// 注册handler
			} else {
				connection.tell(TcpMessage.close(), getSelf());
			}

		} else if (msg instanceof DB_isAllReady) {
			DB_isAllReady db = (DB_isAllReady) msg;
			isAllReady = db.getIsAllReady();
			Tools.printlnInfo("MyEndpoint DB allReady", logger);
		}

	}

}
