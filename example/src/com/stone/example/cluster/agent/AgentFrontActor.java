package com.stone.example.cluster.agent;

import static sample.cluster.transformation.JobMessages.BACKEND_REGISTRATION;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.concurrent.duration.FiniteDuration;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Terminated;
import akka.actor.UntypedActor;

import com.stone.example.cluster.agent.msg.ClientStringRequest;
import com.stone.example.cluster.agent.msg.ClientStringResponse;

/**
 * The agent front actor;
 * 
 * @author crazyjohn
 *
 */
public class AgentFrontActor extends UntypedActor {
	private static final Object TICK = new Object();
	private List<ActorRef> agentProxys = new ArrayList<ActorRef>();
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Random rand = new Random();
	private Cancellable simulater;
	private int counter;

	@Override
	public void preStart() throws Exception {
		FiniteDuration interval = FiniteDuration.create(2, TimeUnit.SECONDS);
		simulater = getContext().system().scheduler().schedule(interval, interval, getSelf(), TICK, getContext().system().dispatcher(), getSelf());
	}

	@Override
	public void postStop() throws Exception {
		simulater.cancel();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof ClientStringRequest && agentProxys.isEmpty()) {
			logger.warn(String.format("Agent proxy is empty!"));
		} else if (msg instanceof ClientStringRequest) {
			// handle packet
			ActorRef agent = getRoutee();
			logger.info(String.format("Route the packet to agent: %s", agent));
			agent.tell(msg, getSelf());
		} else if (msg.equals(BACKEND_REGISTRATION)) {
			getContext().watch(getSender());
			agentProxys.add(getSender());
			logger.info(String.format("Agent register: %s", getSender()));
		} else if (msg instanceof ClientStringResponse) {
			ClientStringResponse response = (ClientStringResponse) msg;
			logger.info(String.format("Response: %s", response.getResult()));
		} else if (msg instanceof Terminated) {
			Terminated terminated = (Terminated) msg;
			agentProxys.remove(terminated.getActor());
		} else if (msg.equals(TICK)) {
			// simulate client
			handleTick();
		}
	}

	private void handleTick() {
		getSelf().tell(new ClientStringRequest(String.format("hi, biatch %d.", counter++)), ActorRef.noSender());
	}

	private ActorRef getRoutee() {
		return agentProxys.get(rand.nextInt(agentProxys.size()));
	}

}
