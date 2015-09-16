package com.stone.game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.core.msg.CGMessage;
import com.stone.core.msg.ProtobufMessage;
import com.stone.core.session.ISession;
import com.stone.game.module.player.PlayerActor;
import com.stone.game.msg.AgentServerInternalMessage;
import com.stone.game.scene.dispatch.SceneDispatcher;
import com.stone.proto.MessageTypes.MessageType;

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

	/** counter */
	private AtomicLong counter = new AtomicLong(0);

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
		if (msg instanceof CGMessage) {
			dispatchToTargetPlayerActor(msg);
		} else if (msg instanceof AgentServerInternalMessage) {
			// msg from agent
			onAgentInternalMessage((AgentServerInternalMessage) msg);
		} else {
			unhandled(msg);
		}
	}

	private void onAgentInternalMessage(AgentServerInternalMessage msg) {
		ProtobufMessage protoMessage = msg.getRealMessage();
		if (protoMessage.getType() == MessageType.CG_SELECT_ROLE_VALUE) {
			ActorRef playerActor = getContext().actorOf(PlayerActor.props(protoMessage.getSession().getSession(), dbMaster),
					"playerActor" + counter.incrementAndGet());
			// watch this player actor
			getContext().watch(playerActor);
			playerActor.forward(protoMessage, getContext());
		}
	}

	/**
	 * Dispatch the msg to target playerActor;
	 * 
	 * @param msg
	 */
	private void dispatchToTargetPlayerActor(Object msg) {
		ActorRef playerActor = ((CGMessage) msg).getPlayerActor();
		if (playerActor == null) {
			ISession sessionInfo = ((CGMessage) msg).getSession();
			logger.info(String.format("Player null, close this session: %s", sessionInfo));
			sessionInfo.close();
			return;
		}
		// put to player actor
		playerActor.tell(msg, ActorRef.noSender());

	}

	public static Props props(ActorRef dbMaster) {
		return Props.create(GameMaster.class, dbMaster);
	}

}
