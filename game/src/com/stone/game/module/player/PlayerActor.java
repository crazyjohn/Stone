package com.stone.game.module.player;

import java.util.concurrent.TimeUnit;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Procedure;

import com.stone.core.actor.msg.ActorSendMessage;
import com.stone.core.data.DataEventBus;
import com.stone.core.msg.ProtobufMessage;
import com.stone.db.annotation.PlayerInternalMessage;
import com.stone.db.entity.HumanItemEntity;
import com.stone.game.scene.dispatch.SceneDispatcher.SceneDispatchEvent;
import com.stone.game.session.msg.GameSessionCloseMessage;
import com.stone.game.session.msg.GameSessionOpenMessage;
import com.stone.proto.common.Commons.Item;

/**
 * The palyer actor;
 * 
 * @author crazyjohn
 *
 */
public class PlayerActor extends UntypedActor {
	private static final String MOCK = "mock";
	/** real player */
	protected final Player player;
	/** db master */
	protected final ActorRef dbMaster;
	/** logger */
	protected Logger logger = LoggerFactory.getLogger(PlayerActor.class);
	/** mock task, just for test */
	final Cancellable mockUpdateTask;

	public PlayerActor(Player player, ActorRef dbMaster) {
		this.player = player;
		this.dbMaster = dbMaster;
		// schedule
		mockUpdateTask = this
				.getContext()
				.system()
				.scheduler()
				.schedule(Duration.create(100, TimeUnit.MILLISECONDS), Duration.create(10, TimeUnit.SECONDS), this.getSelf(), MOCK,
						this.getContext().system().dispatcher(), this.getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof GameSessionOpenMessage) {
			// open session
			GameSessionOpenMessage sessionOpen = (GameSessionOpenMessage) msg;
			sessionOpen.execute();
			// change state
			getContext().become(CONNECTED);
		} else {
			// unhandle msg
			unhandled(msg);
		}
	}

	@Override
	public void postStop() throws Exception {
		// cancel task
		mockUpdateTask.cancel();
	}

	/**
	 * Connected state;
	 */
	private Procedure<Object> CONNECTED = new Procedure<Object>() {

		@Override
		public void apply(Object msg) throws Exception {
			if (msg instanceof GameSessionCloseMessage) {
				getContext().become(DISCONNECTED);
				// forward to player internal modules
				player.onInternalMessage(msg, PlayerActor.this.getSelf());
			} else if (msg instanceof ProtobufMessage) {
				// net message use self execute
				ProtobufMessage netMessage = (ProtobufMessage) msg;
				player.onExternalMessage(netMessage, getSelf(), dbMaster);
			} else if (msg.getClass().getAnnotation(PlayerInternalMessage.class) != null) {
				// handle player internal message
				player.onInternalMessage(msg, getSelf());
			} else if (msg.equals(MOCK)) {
				// FIXME: crazyjohn test code
				// mock update human data
				if (player.getHuman() == null) {
					return;
				}
				HumanItemEntity itemEntity = new HumanItemEntity();
				itemEntity.setHumanGuid(player.getHuman().getGuid());
				itemEntity.getBuilder().setHumanGuid(player.getHuman().getGuid()).setItem(Item.newBuilder().setCount(1).setTemplateId(8888));
				DataEventBus.fireUpdate(dbMaster, getSelf(), itemEntity);
			} else if (msg instanceof ActorSendMessage) {
				ActorSendMessage actorMessage = (ActorSendMessage) msg;
				player.sendMessage(actorMessage.getType(), actorMessage.getBuilder());
			} else if (msg instanceof SceneDispatchEvent) {
				// scene event
				player.onInternalMessage(msg, getSelf());
			} else {
				// unhandled msg
				unhandled(msg);
			}
		}

	};

	/**
	 * Disconnected state;
	 */
	protected Procedure<Object> DISCONNECTED = new Procedure<Object>() {

		@Override
		public void apply(Object msg) throws Exception {
			logger.warn(String.format("PlayerActor in disconnected state now, do not handle any type of message! msg: %s", msg.getClass()
					.getSimpleName()));
		}
	};

	public static Props props(IoSession session, ActorRef dbMaster) {
		Player player = new Player();
		player.setSession(session);
		return Props.create(PlayerActor.class, player, dbMaster);
	}

}
