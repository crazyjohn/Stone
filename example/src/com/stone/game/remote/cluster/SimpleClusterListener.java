package com.stone.game.remote.cluster;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class SimpleClusterListener extends UntypedActor {
	LoggingAdapter logger = Logging.getLogger(this.getContext().system(), this);
	// cluster
	Cluster cluster = Cluster.get(this.getContext().system());

	@Override
	public void preStart() throws Exception {
		cluster.subscribe(getSelf(), ClusterEvent.initialStateAsEvents(), MemberEvent.class, UnreachableMember.class);
	}

	@Override
	public void postStop() throws Exception {
		cluster.unsubscribe(getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof MemberUp) {
			MemberUp up = (MemberUp) msg;
			logger.info("Member is Up: {}", up.member());
		} else if (msg instanceof UnreachableMember) {
			UnreachableMember unreachable = (UnreachableMember) msg;
			logger.info("Member detected as unreadchable: {}", unreachable.member());
		} else if (msg instanceof MemberRemoved) {
			MemberRemoved remove = (MemberRemoved) msg;
			logger.info("Member is removed: {}", remove.member());
		} else if (msg instanceof MemberEvent) {
			// ignore
			logger.info("Member ignore event: {}", msg);
		} else {
			unhandled(msg);
		}
	}

}
