package com.stone.game.module.human.scene;

import akka.actor.ActorRef;

import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.ProtobufMessage;
import com.stone.game.human.Human;
import com.stone.game.module.human.BaseHumanModule;
import com.stone.game.scene.dispatch.SceneDispatchEvent;
import com.stone.game.scene.dispatch.SceneDispatcher;
import com.stone.game.session.msg.GameSessionCloseMessage;
import com.stone.proto.Humans.SceneObjectType;
import com.stone.proto.MessageTypes.MessageType;
import com.stone.proto.Syncs.Move;
import com.stone.proto.Syncs.Move.Builder;
import com.stone.proto.Syncs.SceneObjectAppear;

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

	/**
	 * Handle the sceneEvent;
	 * 
	 * @param sceneEvent
	 */
	private void handleSceneEvent(SceneDispatchEvent sceneEvent) {
		if (sceneEvent.getMessage() instanceof Move.Builder) {
			// move
			Move.Builder move = (Builder) sceneEvent.getMessage();
			if (human.getGuid() == move.getId()) {
				return;
			}
			// send to player
			player.sendMessage(MessageType.GC_BROADCAST_MOVE_VALUE, move);
		} else if (sceneEvent.getMessage() instanceof SceneObjectAppear.Builder) {
			// new human appear
			SceneObjectAppear.Builder appear = (com.stone.proto.Syncs.SceneObjectAppear.Builder) sceneEvent.getMessage();
			if (human.getGuid() == appear.getHuman().getGuid()) {
				return;
			}
			// send to player
			player.sendMessage(MessageType.GC_SCENE_OBJECT_APPEAR_VALUE, appear);
		}
	}

	@Override
	public void onExternalMessage(ProtobufMessage msg, ActorRef playerActor, ActorRef dbMaster) throws MessageParseException {
		if (msg.getType() == MessageType.CG_ENTER_SCENE_READY_VALUE) {
			logger.info(String.format("%s enter scene ready.", human.getName()));
			// enter scene
			SceneDispatcher.getInstance().enterScene(human.getSceneId(), playerActor);
			// new human appear
			SceneObjectAppear.Builder appear = SceneObjectAppear.newBuilder();
			appear.setHuman(generateHumanBuilder());
			appear.setPos(generatePosBuilder());
			SceneDispatcher.getInstance().dispatchSceneEvent(new SceneDispatchEvent(human.getSceneId(), appear));
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

	private Move generatePosBuilder() {
		Move.Builder result = Move.newBuilder();
		result.setId(human.getGuid()).setObjectType(SceneObjectType.HUMAN).setSceneId(human.getSceneId()).setX(300).setY(300);
		return result.build();
	}

	private com.stone.proto.Humans.Human generateHumanBuilder() {
		com.stone.proto.Humans.Human.Builder result = com.stone.proto.Humans.Human.newBuilder();
		result.setGuid(human.getGuid()).setLevel(human.getLevel()).setName(human.getName()).setPlayerId(player.getPlayerId());
		return result.build();
	}

}
