package com.stone.game;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.stone.core.msg.CGMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.core.session.ISession;
import com.stone.game.module.player.PlayerActor;
import com.stone.game.scene.SceneActor;
import com.stone.game.service.ActorService;
import com.stone.game.session.msg.GameSessionCloseMessage;
import com.stone.game.session.msg.GameSessionOpenMessage;

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
			ActorService.registerSceneActor(eachSceneId, getContext().actorOf(Props.create(SceneActor.class), "sceneActor"));
		}
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		// dispatch cg msg
		if (msg instanceof GameSessionOpenMessage) {
			// open session
			GameSessionOpenMessage sessionOpenMsg = (GameSessionOpenMessage) msg;
			onGameSessionOpened(sessionOpenMsg);
		} else if (msg instanceof GameSessionCloseMessage) {
			// close session
			GameSessionCloseMessage sessionClose = (GameSessionCloseMessage) msg;
			onGameSessionClosed(sessionClose);
		} else if (msg instanceof CGMessage) {
			routeToTargetPlayerActor(msg);
		} else {
			unhandled(msg);
		}
	}

	/**
	 * Route the msg to target playerActor;
	 * 
	 * @param msg
	 */
	private void routeToTargetPlayerActor(Object msg) {
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

	/**
	 * On session closed;
	 * 
	 * @param sessionClose
	 * @throws MessageParseException
	 */
	private void onGameSessionClosed(GameSessionCloseMessage sessionClose) throws MessageParseException {
		// remove
		sessionClose.execute();
		// forward
		sessionClose.getPlayerActor().forward(sessionClose, getContext());
		// stop the actor
		// FIXME: crazyjohn at first i use the 'context().stop(subActor)' way,
		// but it's not work, so i change to poision way
		sessionClose.getPlayerActor().tell(PoisonPill.getInstance(), getSender());
		getContext().unwatch(sessionClose.getPlayerActor());
	}

	/**
	 * On game session opened;
	 * 
	 * @param sessionOpenMsg
	 */
	private void onGameSessionOpened(GameSessionOpenMessage sessionOpenMsg) {
		if (sessionOpenMsg.getSession().getPlayerActor() == null) {
			ActorRef playerActor = getContext().actorOf(PlayerActor.props(sessionOpenMsg.getSession().getSession(), dbMaster),
					"playerActor" + counter.incrementAndGet());
			// watch this player actor
			getContext().watch(playerActor);
			sessionOpenMsg.getSession().setPlayerActor(playerActor);
			playerActor.forward(sessionOpenMsg, getContext());
		} else {
			// invalid, close session
			sessionOpenMsg.getSession().close();
		}
	}

	public static Props props(ActorRef dbMaster) {
		return Props.create(GameMaster.class, dbMaster);
	}

}
