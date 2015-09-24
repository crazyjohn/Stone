package com.stone.example.cluster.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;

/**
 * The real agent;
 * 
 * @author crazyjohn
 *
 */
public class AgentBackActor extends UntypedActor {
	private Cluster cluster;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void preStart() throws Exception {
		cluster = Cluster.get(getContext().system());
		// subscribe
		cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class);
	}

	@Override
	public void postStop() throws Exception {
		// unsubscribe
		cluster.unsubscribe(getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof MemberUp) {
			MemberUp up = (MemberUp) msg;
			logger.info(String.format("Yo Yo Yo, receiver: %s, member is up: %s", getSelf(), up.member()));
		} else if (msg instanceof MemberRemoved) {
			MemberRemoved remove = (MemberRemoved) msg;
			logger.info(String.format("Yo Yo Yo, receiver: %s, member is remove: %s", getSelf(), remove.member()));
		} else if (msg instanceof MemberEvent) {
			MemberEvent event = (MemberEvent) msg;
			logger.info(String.format("Yo Yo Yo, receive memberEvent, member: %s, event: %s", event.member(), event));
		}
	}

}
