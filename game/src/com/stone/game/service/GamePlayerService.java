package com.stone.game.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.Message.Builder;
import com.stone.game.module.player.Player;
import com.stone.proto.Humans.Human;
import com.stone.proto.Syncs.Sync;

public class GamePlayerService {
	private Map<Long, Player> players = new ConcurrentHashMap<Long, Player>();

	public Builder buildSync(long playerId) {
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

	public void addPlayer(Player player) {
		players.put(player.getPlayerId(), player);
	}

	public void removePlayer(Player player) {
		players.remove(player.getPlayerId());
	}

}
