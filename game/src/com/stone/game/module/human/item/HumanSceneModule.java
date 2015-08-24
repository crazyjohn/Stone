package com.stone.game.module.human.item;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.ProtobufMessage;
import com.stone.game.human.Human;
import com.stone.game.module.human.BaseHumanModule;
import com.stone.game.scene.dispatch.SceneDispatcher;
import com.stone.game.scene.dispatch.SceneDispatcher.SceneDispatchEvent;
import com.stone.game.session.msg.GameSessionCloseMessage;
import com.stone.proto.MessageTypes.MessageType;
import com.stone.proto.Syncs.Move;
import com.stone.proto.Syncs.Move.Builder;

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
			SceneDispatcher.getInstance().leaveScene(human.getSceneId(), playerActor);
		} else if (msg instanceof SceneDispatchEvent) {
			// handle event
			SceneDispatchEvent sceneEvent = (SceneDispatchEvent) msg;
			handleSceneEvent(sceneEvent);
		}
	}

	private void handleSceneEvent(SceneDispatchEvent sceneEvent) {
		if (sceneEvent.getBuilder() instanceof Move.Builder) {
			// move
			Move.Builder move = (Builder) sceneEvent.getBuilder();
			if (human.getGuid() == move.getId()) {
				return;
			}
			// send to player
			player.sendMessage(MessageType.GC_BROADCAST_MOVE_VALUE, move);
		}
	}

	@Override
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) throws MessageParseException {
		if (msg.getType() == MessageType.CG_ENTER_SCENE_READY_VALUE) {
			logger.info(String.format("%s enter scene ready.", human.getName()));
			// enter scene
			SceneDispatcher.getInstance().enterScene(human.getSceneId(), playerActor);

		} else if (msg.getType() == MessageType.CG_SYNC_VALUE) {
			// not support
			throw new UnsupportedOperationException("Do not support CG_SYNC_VALUE request");
		} else if (msg.getType() == MessageType.CG_REQUEST_MOVE_VALUE) {
			// move
			Move.Builder move = msg.parseBuilder(Move.newBuilder());
			// publish to scene humans
			SceneDispatcher.getInstance().dispatchSceneEvent(new SceneDispatchEvent(move.getSceneId(), move));
			logger.info(String.format("%s request moveTo (x: %d, y: %d)", human.getName(), move.getX(), move.getY()));
		}
	}

}
