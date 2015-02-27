package com.i4joy.akka.kok.sharding;

import java.util.HashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.concurrent.duration.Duration;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.camel.worker.LoginWorker;
import com.i4joy.akka.kok.io.protocol.Heart;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.contrib.pattern.DistributedPubSubExtension;

public class ShardingMaster extends UntypedActor {
	protected final Log logger = LogFactory.getLog(getClass());

	final ActorRef mediator = DistributedPubSubExtension.get(getContext().system()).mediator();
	private Cancellable heart;// 心跳
	private HashMap<String, ActorRef> areanSingleServers = new HashMap<String, ActorRef>();
	private HashMap<String, ActorRef> pillageSingleServers = new HashMap<String, ActorRef>();

	public ShardingMaster() {

	}

	@Override
	public void preStart() throws Exception {
		this.heart = getContext().system().scheduler()// 心跳 每10秒更新一次
				.schedule(Duration.create(10, "seconds"), Duration.create(10, "seconds"), getSelf(), Heart.instance, getContext().dispatcher(), getSelf());
		super.preStart();
	}

	public static Props props() {
		return Props.create(ShardingMaster.class);
	}

	@Override
	public void postStop() throws Exception {
		heart.cancel();
		super.postStop();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof Heart) {
			JSONArray o = (JSONArray) LoginWorker.getServers(mediator).get("servers");
			String name;
			for (int j = 0; j < o.size(); j++) {
				JSONObject s = (JSONObject) o.get(j);
				name = s.getString("name");
				if (!areanSingleServers.containsKey(name)) {
					areanSingleServers.put(name, getContext().actorOf(ArenaSingleServer.props(Property.SHARDING + name)));
				}
				if (!pillageSingleServers.containsKey(name)) {
					pillageSingleServers.put(name, getContext().actorOf(PillageSingleServer.props(Property.SHARDING + name)));
				}
			}
		}
	}

}
