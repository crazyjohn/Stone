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
import com.stone.agent.msg.external.CGMessage;
import com.stone.agent.msg.external.ClientSessionCloseMessage;
import com.stone.agent.msg.external.ClientSessionOpenMessage;
import com.stone.agent.msg.internal.RegisterAgentPlayer;
import com.stone.agent.msg.internal.UnRegisterAgentPlayer;
import com.stone.agent.player.AgentPlayer;
import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.ProtobufMessage;
import com.stone.core.msg.server.AGForwardMessage;
import com.stone.core.msg.server.GCMessage;
import com.stone.core.msg.server.ServerBetweenMessage;
import com.stone.core.session.BaseActorSession;
import com.stone.proto.MessageTypes.MessageType;
import com.stone.proto.Servers.GameRegisterToAgent;

/**
 * The agent master actor;
 * 
 * @author crazyjohn
 *
 */
public class AgentMaster extends UntypedActor {
	private final ActorRef dbMaster;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/** counter */
	private AtomicLong counter = new AtomicLong(0);
	/** the game server sessions */
	private Map<Integer, BaseActorSession> gameServerSessions = new HashMap<Integer, BaseActorSession>();
	private Map<Long, ActorRef> playerActors = new HashMap<Long, ActorRef>();

	public AgentMaster(ActorRef dbMaster) {
		this.dbMaster = dbMaster;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof ClientSessionOpenMessage) {
			// open session
			ClientSessionOpenMessage sessionOpenMsg = (ClientSessionOpenMessage) msg;
			onClientSessionOpened(sessionOpenMsg);
		} else if (msg instanceof ClientSessionCloseMessage) {
			// close session
			ClientSessionCloseMessage sessionClose = (ClientSessionCloseMessage) msg;
			onClientSessionClosed(sessionClose);
		} else if (msg instanceof CGMessage) {
			// dispatch to player actor
			dispatchClientMessageToTargetActor((CGMessage) msg);
		} else if (msg instanceof ServerBetweenMessage) {
			// handle server internal msg
			onServerInternalMessage((ProtobufMessage) msg);
		} else if (msg instanceof AGForwardMessage) {
			// forward to game server
			AGForwardMessage forwardMessage = (AGForwardMessage) msg;
			onAGForwardMessage(forwardMessage);
		} else if (msg instanceof GCMessage) {
			// handle GAForward message
			GCMessage forwardMessge = (GCMessage) msg;
			onGCMessage(forwardMessge);
		} else if (msg instanceof RegisterAgentPlayer) {
			RegisterAgentPlayer register = (RegisterAgentPlayer) msg;
			rgisterPlayerActor(register.getPlayerId(), getSender());
		} else if (msg instanceof UnRegisterAgentPlayer) {
			UnRegisterAgentPlayer register = (UnRegisterAgentPlayer) msg;
			unRegisterPlayerActor(register.getPlayerId());
			// kill actor
			getSender().tell(PoisonPill.getInstance(), getSender());
			getContext().unwatch(getSender());
		} else {
			unhandled(msg);
		}
	}

	private void onAGForwardMessage(AGForwardMessage forwardMessage) {
		BaseActorSession session = this.gameServerSessions.get(forwardMessage.getSceneId());
		if (session == null) {
			return;
		}
		session.getSession().write(forwardMessage);
	}

	private void rgisterPlayerActor(long playerId, ActorRef actor) {
		this.playerActors.put(playerId, actor);
	}

	private void unRegisterPlayerActor(long playerId) {
		this.playerActors.remove(playerId);
	}

	private void onGCMessage(GCMessage msg) throws Exception {
		ActorRef actor = this.playerActors.get(msg.getPlayerId());
		if (actor == null) {
			logger.warn(String.format("No such player actor: %d, type: %d", msg.getPlayerId(), msg.getType()));
			return;
		}
		actor.tell(msg, ActorRef.noSender());
	}

	/**
	 * Dispatch the CAMessage to target actor;
	 * 
	 * @param msg
	 */
	private void dispatchClientMessageToTargetActor(CGMessage msg) {
		msg.getPlayerActor().tell(msg, getSelf());
	}

	/**
	 * Handle server internal message;
	 * 
	 * @param msg
	 * @throws MessageParseException
	 */
	private void onServerInternalMessage(ProtobufMessage msg) throws Exception {

		if (msg.getType() == MessageType.GAME_REGISTER_TO_AGENT_VALUE) {
			BaseActorSession gameProxySession = msg.getSession();
			GameRegisterToAgent.Builder register = msg.getBuilder(GameRegisterToAgent.newBuilder());
			for (int sceneId : register.getSceneIdsList()) {
				this.gameServerSessions.put(sceneId, gameProxySession);
			}
			logger.info(JsonFormat.printToString(register.build()));
		}
	}

	private void onClientSessionClosed(ClientSessionCloseMessage sessionClose) throws MessageParseException {
		// remove
		sessionClose.execute();
		// forward
		sessionClose.getPlayerActor().tell(sessionClose, getSelf());
	}

	private void onClientSessionOpened(ClientSessionOpenMessage sessionOpenMsg) {
		if (sessionOpenMsg.getSession().getActor() == null) {
			ActorRef gatePlayerActor = getContext().actorOf(
					Props.create(AgentPlayerActor.class, this.dbMaster, new AgentPlayer(sessionOpenMsg.getSession().getSession())),
					"agentPlayerActor" + counter.incrementAndGet());
			// watch this player actor
			getContext().watch(gatePlayerActor);
			sessionOpenMsg.getSession().setActor(gatePlayerActor);
		} else {
			// invalid, close session
			sessionOpenMsg.getSession().close();
		}
	}
}
