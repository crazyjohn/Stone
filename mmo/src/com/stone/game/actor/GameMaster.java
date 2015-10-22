package com.stone.game.actor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;

import com.stone.core.msg.ProtobufMessage;
import com.stone.core.msg.server.AGForwardMessage;
import com.stone.core.session.BaseActorSession;
import com.stone.game.module.player.GamePlayerActor;
import com.stone.game.scene.dispatch.SceneDispatcher;
import com.stone.game.server.msg.AGPlayerLogoutMessage;
import com.stone.game.server.msg.AgentSessionOpenMessage;
import com.stone.proto.MessageTypes.MessageType;
import com.stone.proto.Servers.GameRegisterToAgent;
import com.stone.proto.Servers.ServerInfo;
import com.stone.proto.Servers.ServerType;

/**
 * The master actor;
 * 
 * @author crazyjohn
 *
 */
public class GameMaster extends UntypedActor {
	/** loggers */
	private Logger logger = LoggerFactory.getLogger(GameMaster.class);
	/** dbMaster */
	private final ActorRef dbMaster;
	/** mock scene data */
	private List<Integer> sceneDatas = new ArrayList<Integer>();
	protected BaseActorSession agentSession;
	private Map<Long, ActorRef> playerActors = new HashMap<Long, ActorRef>();

	public GameMaster(ActorRef dbMaster) {
		this.dbMaster = dbMaster;
		// init scene
		initScene();
	}

	private void initScene() {
		int defatultSceneId = 1;
		sceneDatas.add(defatultSceneId);
		for (int eachSceneId : sceneDatas) {
			SceneDispatcher.getInstance().registerSceneEventBus(eachSceneId);
		}
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		// dispatch cg msg
		if (msg instanceof AGPlayerLogoutMessage) {
			AGPlayerLogoutMessage logout = (AGPlayerLogoutMessage) msg;
			onPlayerLogout(logout);
		} else if (msg instanceof AGForwardMessage) {
			// msg from agent
			onAgentForwardMessage((AGForwardMessage) msg);
		} else if (msg instanceof AgentSessionOpenMessage) {
			// agent session opend
			onAgentSessionOpened((AgentSessionOpenMessage) msg);
		} else if (msg instanceof Terminated) {
			// when received terminated msg
			Terminated terminated = (Terminated) msg;
			this.getContext().unwatch(terminated.actor());
			logger.info(String.format("UnRegister player actor: %s", terminated.actor()));
		} else {
			unhandled(msg);
		}
	}

	private void onPlayerLogout(AGPlayerLogoutMessage logout) {
		// TODO: crazyjohn find bugs???
		ActorRef actor = getPlayerActor(logout.getPlayerId());
		actor.tell(logout, getSelf());
		actor.tell(PoisonPill.getInstance(), getSender());
		// getContext().unwatch(actor);
		this.playerActors.remove(logout.getPlayerId());
	}

	private void onAgentSessionOpened(AgentSessionOpenMessage msg) {
		this.agentSession = msg.getSession();
		logger.info("Start to register on agent server...");
		ProtobufMessage message = new ProtobufMessage(MessageType.GAME_REGISTER_TO_AGENT_VALUE);
		message.setBuilder(GameRegisterToAgent.newBuilder().setServerInfo(ServerInfo.newBuilder().setName("game").setType(ServerType.GAME))
				.addSceneIds(1));
		msg.getSession().writeMessage(message);
	}

	private void onAgentForwardMessage(AGForwardMessage msg) {
		long playerId = msg.getPlayerId();
		if (msg.getType() == MessageType.CG_SELECT_ROLE_VALUE) {
			// create player actor when received select char msg
			ActorRef playerActor = getContext().actorOf(GamePlayerActor.props(msg.getSession().getSession(), dbMaster, playerId),
					"playerActor_" + playerId);
			// watch this player actor
			getContext().watch(playerActor);
			logger.info(String.format("Register player actor: %s", playerActor));
			this.playerActors.put(playerId, playerActor);
			playerActor.forward(msg, getContext());
		} else {
			ActorRef playerActor = getPlayerActor(msg.getPlayerId());
			// put to player actor
			playerActor.tell(msg, ActorRef.noSender());
		}
	}

	private ActorRef getPlayerActor(long playerId) {
		ActorRef actor = this.playerActors.get(playerId);
		return actor;
	}

	public static Props props(ActorRef dbMaster) {
		return Props.create(GameMaster.class, dbMaster);
	}

}
