package com.i4joy.akka.kok.monster;

import java.util.concurrent.ConcurrentHashMap;

import com.i4joy.akka.kok.overlord.protocol.PlayerCreate;

import akka.actor.ActorRef;
import akka.actor.UntypedActorContext;

public class MonsterWatchList {
	// key :playerId value:address
	private ConcurrentHashMap<Integer, String> playerIdAddressHM = new ConcurrentHashMap<Integer, String>();
	// key:playerId value:ActorRef
	private ConcurrentHashMap<Integer, ActorRef> playerIdCacheHM = new ConcurrentHashMap<Integer, ActorRef>();
	// key :playerName value:address
	private ConcurrentHashMap<String, String> playerNameAddressHM = new ConcurrentHashMap<String, String>();
	// key:playerName value:ActorRef
	private ConcurrentHashMap<String, ActorRef> playerNameCacheHM = new ConcurrentHashMap<String, ActorRef>();

	public void addPlayerActor(PlayerCreate pc, String playersGroupName, UntypedActorContext context) {
		ActorRef playerActor = context.actorOf(PlayerActor.props("" + pc.getPlayerId(), pc.getPlayerName(), pc.getServerId(), playersGroupName));
		playerIdCacheHM.put(pc.getPlayerId(), playerActor);
		playerNameCacheHM.put(pc.getPlayerName(), playerActor);
	}

	public void addPlayerAddress(String playerName, int playerId, String playersGroupName) {
		playerIdAddressHM.put(playerId, playersGroupName);
		playerNameAddressHM.put(playerName, playersGroupName);
	}

	public void removePlayerActor(int playerId, String playerName, UntypedActorContext context) {
		if (playerId != 0) {
			ActorRef playerActor = playerIdCacheHM.get(playerId);
			if (playerActor != null) {
				context.stop(playerActor);
				playerIdCacheHM.remove(playerId);
			}
		}
		if (playerName != null) {
			ActorRef playerActor = playerNameCacheHM.get(playerName);
			if (playerActor != null) {
				context.stop(playerActor);
				playerNameCacheHM.remove(playerName);
			}
		}
	}

	public void removePlayerAddress(int playerId, String playerName) {
		if (playerId != 0) {
			playerIdAddressHM.remove(playerId);
		}
		if (playerName != null) {
			playerNameAddressHM.remove(playerName);
		}
	}

	public ActorRef getPlayerActorById(int playerId) {
		return playerIdCacheHM.get(playerId);
	}

	public ActorRef getPlayerActorByName(String playerName) {
		return playerNameCacheHM.get(playerName);
	}

	public String getPlayerAddressById(int playerId) {
		return playerIdAddressHM.get(playerId);
	}

	public String getPlayerAddressByName(String playerName) {
		return playerNameAddressHM.get(playerName);
	}

	public int getSize() {
		return playerIdCacheHM.size();
	}

	public ConcurrentHashMap<Integer, String> getPlayerIdAddressHM() {
		return playerIdAddressHM;
	}

	public void setPlayerIdAddressHM(ConcurrentHashMap<Integer, String> playerIdAddressHM) {
		this.playerIdAddressHM = playerIdAddressHM;
	}

	public ConcurrentHashMap<Integer, ActorRef> getPlayerIdCacheHM() {
		return playerIdCacheHM;
	}

	public void setPlayerIdCacheHM(ConcurrentHashMap<Integer, ActorRef> playerIdCacheHM) {
		this.playerIdCacheHM = playerIdCacheHM;
	}

	public ConcurrentHashMap<String, String> getPlayerNameAddressHM() {
		return playerNameAddressHM;
	}

	public void setPlayerNameAddressHM(ConcurrentHashMap<String, String> playerNameAddressHM) {
		this.playerNameAddressHM = playerNameAddressHM;
	}

	public ConcurrentHashMap<String, ActorRef> getPlayerNameCacheHM() {
		return playerNameCacheHM;
	}

	public void setPlayerNameCacheHM(ConcurrentHashMap<String, ActorRef> playerNameCacheHM) {
		this.playerNameCacheHM = playerNameCacheHM;
	}

}
