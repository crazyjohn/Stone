package com.stone.agent.actor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.googlecode.protobuf.format.JsonFormat;
import com.stone.agent.msg.internal.AgentSessionCloseMessage;
import com.stone.agent.msg.internal.AgentSessionOpenMessage;
import com.stone.agent.msg.internal.SelectRoleFromGame;
import com.stone.agent.player.AgentPlayer;
import com.stone.core.msg.CGMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.ProtobufMessage;
import com.stone.core.msg.server.ServerInternalMessage;
import com.stone.core.session.BaseActorSession;
import com.stone.proto.MessageTypes.MessageType;
import com.stone.proto.Servers.GameRegisterToAgent;

public class AgentMaster extends UntypedActor {
	private final ActorRef dbMaster;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/** counter */
	private AtomicLong counter = new AtomicLong(0);
	/** the game server sessions */
	private Map<Integer, BaseActorSession> gameServerSessions = new HashMap<Integer, BaseActorSession>();

	public AgentMaster(ActorRef dbMaster) {
		this.dbMaster = dbMaster;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof AgentSessionOpenMessage) {
			// open session
			AgentSessionOpenMessage sessionOpenMsg = (AgentSessionOpenMessage) msg;
			onGateSessionOpened(sessionOpenMsg);
		} else if (msg instanceof AgentSessionCloseMessage) {
			// close session
			AgentSessionCloseMessage sessionClose = (AgentSessionCloseMessage) msg;
			onGateSessionClosed(sessionClose);
		} else if (msg instanceof CGMessage) {
			// dispatch to player actor
			dispatchToTargetPlayerActor((CGMessage) msg);
		} else if (msg instanceof ServerInternalMessage) {
			// handle server internal msg
			onServerInternalMessage((ServerInternalMessage) msg);
		} else if (msg instanceof SelectRoleFromGame) {
			SelectRoleFromGame select = (SelectRoleFromGame) msg;
			BaseActorSession session = this.gameServerSessions.get(select.getSceneId());
			if (session == null) {
				return;
			}
			session.getSession().write(select.getProtobufMessage());
		} else {
			unhandled(msg);
		}
	}

	private void dispatchToTargetPlayerActor(CGMessage msg) {
		msg.getPlayerActor().tell(msg, getSelf());
	}

	/**
	 * Handle server internal message;
	 * 
	 * @param msg
	 * @throws MessageParseException
	 */
	private void onServerInternalMessage(ServerInternalMessage msg) throws MessageParseException {
		ProtobufMessage protoMessage = msg.getRealMessage();
		if (protoMessage.getType() == MessageType.GAME_REGISTER_TO_AGENT_VALUE) {
			BaseActorSession gameProxySession = protoMessage.getSession();
			GameRegisterToAgent.Builder register = protoMessage.parseBuilder(GameRegisterToAgent.newBuilder());
			for (int sceneId : register.getSceneIdsList()) {
				this.gameServerSessions.put(sceneId, gameProxySession);
			}
			logger.info(JsonFormat.printToString(register.build()));
		}
	}

	private void onGateSessionClosed(AgentSessionCloseMessage sessionClose) throws MessageParseException {
		// remove
		sessionClose.execute();
		// forward
		sessionClose.getPlayerActor().forward(sessionClose, getContext());
		// stop the actor
		// FIXME: crazyjohn at first i use the 'context().stop(subActor)' way,
		// but it's not work, so i change to poison way
		sessionClose.getPlayerActor().tell(PoisonPill.getInstance(), getSender());
		getContext().unwatch(sessionClose.getPlayerActor());

	}

	private void onGateSessionOpened(AgentSessionOpenMessage sessionOpenMsg) {
		if (sessionOpenMsg.getSession().getActor() == null) {
			ActorRef gatePlayerActor = getContext().actorOf(
					Props.create(AgentPlayerActor.class, this.dbMaster, new AgentPlayer(sessionOpenMsg.getSession().getSession())),
					"gatePlayerActor" + counter.incrementAndGet());
			// watch this player actor
			getContext().watch(gatePlayerActor);
			sessionOpenMsg.getSession().setActor(gatePlayerActor);
			gatePlayerActor.forward(sessionOpenMsg, getContext());
		} else {
			// invalid, close session
			sessionOpenMsg.getSession().close();
		}
	}
}
