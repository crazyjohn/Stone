package com.stone.example.cluster.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.MemberEvent;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.cluster.Member;
import akka.cluster.MemberStatus;

import com.stone.example.cluster.agent.msg.ClientStringRequest;
import com.stone.example.cluster.agent.msg.ClientStringResponse;

/**
 * The real agent;
 * 
 * @author crazyjohn
 *
 */
public class AgentBackActor extends UntypedActor {
	private Cluster cluster;
	private Logger logger = LoggerFactory.getLogger(getClass());
	public static final String BACKEND_REGISTRATION = "BackendRegistration";

	@Override
	public void preStart() throws Exception {
		cluster = Cluster.get(getContext().system());
		// subscribe
		cluster.subscribe(getSelf(), MemberEvent.class, UnreachableMember.class, MemberUp.class);
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
			register(up.member());
		} else if (msg instanceof MemberRemoved) {
			MemberRemoved remove = (MemberRemoved) msg;
			logger.info(String.format("Yo Yo Yo, receiver: %s, member is remove: %s", getSelf(), remove.member()));
		} else if (msg instanceof ClientStringRequest) {
			logger.info(String.format("Received packet from: %s", getSender()));
			ClientStringRequest packet = (ClientStringRequest) msg;
			handlePacket(packet);
		} else if (msg instanceof CurrentClusterState) {
			// Current snapshot state of the cluster. Sent to new subscriber.
			CurrentClusterState state = (CurrentClusterState) msg;
			for (Member member : state.getMembers()) {
				if (member.status().equals(MemberStatus.up())) {
					register(member);
				}
			}

		}
	}

	private void register(Member member) {
		if (member.hasRole("frontend"))
			getContext().actorSelection(member.address() + "/user/frontend").tell(BACKEND_REGISTRATION, getSelf());
	}

	private void handlePacket(ClientStringRequest packet) {
		logger.debug("Sender: " + getSender());
		getSender().tell(new ClientStringResponse(packet.getInfo().toUpperCase() + "_FUCKED"), getSelf());

	}

}
