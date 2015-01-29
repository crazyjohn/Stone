package com.stone.actor.player.equip.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.actor.future.IActorFuture;
import com.stone.actor.listener.IActorFutureListener;
import com.stone.actor.player.PlayerActor;
import com.stone.actor.player.equip.PlayerEquip;
import com.stone.actor.player.equip.msg.CGSeeOtherPlayerEquip;
import com.stone.actor.system.ActorSystem;

public class PlayerSeeOtherPlayerEquipHandler {
	private Logger logger = LoggerFactory.getLogger(PlayerSeeOtherPlayerEquipHandler.class);

	public void execute(CGSeeOtherPlayerEquip msg) {
		// get params
		long playerId = msg.getPlayerId();
		long equipId = msg.getEquipId();
		final PlayerActor player = msg.getPlayer();
		PlayerActor otherPlayer = ActorSystem.getInstance().getPlayerActor(playerId);
		// get future
		IActorFuture<PlayerEquip> future = otherPlayer.getPlayerEquip(equipId);
		// add listener
		future.addListener(new IActorFutureListener<PlayerEquip>() {
			@Override
			public void onComplete(IActorFuture<PlayerEquip> future) {
				PlayerEquip equip = future.getResult();
				logger.info(String.format("Player id:%d see the equip equipId:%d", player.getId(), equip.getId()));
			}
		});
	}
}
