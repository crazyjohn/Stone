package com.i4joy.akka.kok.sharding;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.duration.Duration;

import com.i4joy.akka.kok.io.protocol.Heart;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;
import akka.contrib.pattern.DistributedPubSubMediator;

public class PillageSingleServer extends UntypedActor {

	public static Props props(String serverName) {
		return Props.create(PillageSingleServer.class, serverName);
	}

	protected final Log logger = LogFactory.getLog(getClass());
	private Cancellable heart;// 心跳
	private ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
	private String serverName;

	public PillageSingleServer(String serverName) {
		this.serverName = serverName;
	}

	@Override
	public void preStart() throws Exception {
		this.heart = getContext().system().scheduler()// 心跳 每10秒更新一次
				.schedule(Duration.create(10, "seconds"), Duration.create(10, "seconds"), getSelf(), new Heart(), getContext().dispatcher(), getSelf());
		mediator.tell(new DistributedPubSubMediator.Subscribe(serverName, getSelf()), getSelf());
		super.preStart();
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		mediator.tell(new DistributedPubSubMediator.Unsubscribe(serverName, getSelf()), getSelf());
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {

	}

}
