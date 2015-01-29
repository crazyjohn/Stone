package com.stone.actor.player.equip.handler;

import com.stone.actor.call.IActorCall;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.listener.IActorFutureListener;
import com.stone.actor.player.PlayerActor;
import com.stone.actor.player.equip.msg.CGPlayerSendEquip;
import com.stone.actor.player.equip.msg.GCPlayerSendEquipResult;
import com.stone.actor.system.ActorSystem;

public class PlayerSendEquipHandler {
	public void execute(CGPlayerSendEquip msg) {
		long senderPlayerId = msg.getSender();
		final long receiverPlayerId = msg.getReceiver();
		final long equipId = msg.getEquipId();
		final PlayerActor senderPlayer = ActorSystem.getInstance().getPlayerActor(senderPlayerId);
		if (senderPlayer == null) {
			return;
		}
		if (!senderPlayer.getPlayerEquipModule().hasSuchEquip(equipId)) {
			return;
		}
		// make actor call
		final PlayerActor receiverPlayer = ActorSystem.getInstance().getPlayerActor(receiverPlayerId);
		IActorFuture<Boolean> future = receiverPlayer.call(new IActorCall<Boolean>() {
			@Override
			public Boolean execute() {
				boolean succeed = receiverPlayer.getPlayerEquipModule().addEquip(equipId);
				return succeed;
			}
		});
		// add future listener
		future.addListener(new IActorFutureListener<Boolean>() {
			@Override
			public void onComplete(IActorFuture<Boolean> future) {
				GCPlayerSendEquipResult resultMsg = new GCPlayerSendEquipResult();
				resultMsg.setEquipId(equipId);
				resultMsg.setReceiverId(receiverPlayerId);
				resultMsg.setSendResult(future.getResult());
				senderPlayer.sendMessage(resultMsg);
			}
		});
	}
}
