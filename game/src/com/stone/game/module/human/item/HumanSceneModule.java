package com.stone.game.module.human.item;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.ProtobufMessage;
import com.stone.game.human.Human;
import com.stone.game.module.human.BaseHumanModule;
import com.stone.game.scene.SceneActor.RegisterPlayer;
import com.stone.game.scene.SceneActor.RegisterPlayerActor;
import com.stone.game.scene.SceneActor.SyncPlayers;
import com.stone.game.scene.SceneActor.UnRegisterPlayer;
import com.stone.game.scene.SceneActor.UnRegisterPlayerActor;
import com.stone.game.service.SceneActorRegistry;
import com.stone.game.session.msg.GameSessionCloseMessage;
import com.stone.proto.MessageTypes.MessageType;

/**
 * The human's scene module;
 * 
 * @author crazyjohn
 *
 */
public class HumanSceneModule extends BaseHumanModule {

	public HumanSceneModule(Human human) {
		super(human);
	}

	@Override
	public void onInternalMessage(Object msg, ActorRef playerActor) {
		if (msg instanceof GameSessionCloseMessage) {
			// leave scene
			ActorRef sceneActor = SceneActorRegistry.getInstance().getSceneActor(player.getHuman().getSceneId());
			sceneActor.tell(new UnRegisterPlayer(player), ActorRef.noSender());
			sceneActor.tell(new UnRegisterPlayerActor(player.getPlayerId()), ActorRef.noSender());
		}
	}

	@Override
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) throws MessageParseException {
		if (msg.getType() == MessageType.CG_ENTER_SCENE_READY_VALUE) {
			logger.info(String.format("%s enter scene ready.", human.getName()));
			// enter scene
			ActorRef sceneActor = getCurrentScene(human.getSceneId());
			sceneActor.tell(new RegisterPlayer(this.player), ActorRef.noSender());
			sceneActor.tell(new RegisterPlayerActor(this.player.getPlayerId(), playerActor), ActorRef.noSender());

		} else if (msg.getType() == MessageType.CG_SYNC_VALUE) {
			// sync
			ActorRef sceneActor = getCurrentScene(human.getSceneId());
			sceneActor.tell(new SyncPlayers(this.player.getPlayerId()), ActorRef.noSender());
		}
	}

	private ActorRef getCurrentScene(int sceneId) {
		return SceneActorRegistry.getInstance().getSceneActor(sceneId);
	}

}
