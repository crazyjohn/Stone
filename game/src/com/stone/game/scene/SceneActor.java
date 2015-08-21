package com.stone.game.scene;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.google.protobuf.Message.Builder;
import com.stone.core.actor.msg.ActorSendMessage;
import com.stone.core.concurrent.annotation.GuardedByUnit;
import com.stone.game.module.player.Player;
import com.stone.proto.Humans.Human;
import com.stone.proto.MessageTypes.MessageType;
import com.stone.proto.Syncs.Sync;

/**
 * The game player manager;
 * <p>
 * FIXME: crazyjohn maybe i should use actor to replace the thread way?
 * <ul>
 * <li>manage the playerActors</li>
 * <li>do broadcast just like chat</li>
 * </ul>
 * 
 * @author crazyjohn
 *
 */
public class SceneActor extends UntypedActor {
	/**
	 * must use ConcurrentHashMap, because we hold the player datas which would
	 * be write by another thread
	 */
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	private Map<Long, Player> players = new ConcurrentHashMap<Long, Player>();
	private Map<Long, ActorRef> playerActors = new HashMap<Long, ActorRef>();
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Builder buildSync(long playerId) {
		Sync.Builder result = Sync.newBuilder();
		for (Player eachPlayer : players.values()) {
			if (eachPlayer.getPlayerId() == playerId) {
				continue;
			}
			Human.Builder eachHuman = Human.newBuilder();
			eachHuman.setGuid(eachPlayer.getHuman().getGuid()).setLevel(eachPlayer.getHuman().getLevel()).setName(eachPlayer.getHuman().getName())
					.setPlayerId(eachPlayer.getPlayerId());
			result.addHumans(eachHuman);
		}
		result.setSyncTime(System.currentTimeMillis());
		return result;
	}

	private void addPlayer(Player player) {
		logger.info("Add player: " + player.getPlayerId());
		players.put(player.getPlayerId(), player);
	}

	private void removePlayer(Player player) {
		players.remove(player.getPlayerId());
		logger.info("Remove player: " + player.getPlayerId());

	}

	private void addPlayerActor(long playerId, ActorRef playerActor) {
		playerActors.put(playerId, playerActor);
	}

	private void removePlayerActor(long playerId) {
		this.playerActors.remove(playerId);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof RegisterPlayer) {
			// add player
			RegisterPlayer register = (RegisterPlayer) msg;
			this.addPlayer(register.getPlayer());
		} else if (msg instanceof UnRegisterPlayer) {
			// remove player
			UnRegisterPlayer unRegister = (UnRegisterPlayer) msg;
			this.removePlayer(unRegister.getPlayer());
		} else if (msg instanceof RegisterPlayerActor) {
			// add actor
			RegisterPlayerActor register = (RegisterPlayerActor) msg;
			this.addPlayerActor(register.getPlayerId(), register.getActor());
		} else if (msg instanceof UnRegisterPlayerActor) {
			// remove actor
			UnRegisterPlayerActor unRegister = (UnRegisterPlayerActor) msg;
			this.removePlayerActor(unRegister.getPlayerId());
		} else if (msg instanceof SyncPlayers) {
			SyncPlayers sync = (SyncPlayers) msg;
			logger.debug(String.format("%d request sync.", sync.getPlayerId()));
			ActorRef playerActor = this.playerActors.get(sync.getPlayerId());
			if (playerActor == null) {
				return;
			}
			playerActor.tell(new ActorSendMessage(MessageType.GC_SYNC_VALUE, this.buildSync(sync.getPlayerId())), ActorRef.noSender());
		}
	}

	public static class RegisterPlayer {
		private final Player player;

		public RegisterPlayer(Player player) {
			this.player = player;
		}

		public Player getPlayer() {
			return player;
		}
	}

	public static class UnRegisterPlayer {
		private final Player player;

		public UnRegisterPlayer(Player player) {
			this.player = player;
		}

		public Player getPlayer() {
			return player;
		}
	}

	public static class RegisterPlayerActor {
		private final long id;
		private final ActorRef actor;

		public RegisterPlayerActor(long id, ActorRef actor) {
			this.id = id;
			this.actor = actor;
		}

		public long getPlayerId() {
			return id;
		}

		public ActorRef getActor() {
			return actor;
		}
	}

	public static class UnRegisterPlayerActor {
		private final long id;

		public UnRegisterPlayerActor(long id) {
			this.id = id;
		}

		public long getPlayerId() {
			return id;
		}
	}

	public static class SyncPlayers {
		private final long id;

		public SyncPlayers(long playerId) {
			this.id = playerId;
		}

		public long getPlayerId() {
			return id;
		}

	}

}
