package com.stone.actor.player.equip.handler;

import com.stone.actor.call.IActorCall;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.id.ActorId;
import com.stone.actor.id.ActorType;
import com.stone.actor.id.IActorId;
import com.stone.actor.listener.IActorFutureListener;
import com.stone.actor.player.PlayerActor;
import com.stone.actor.player.equip.msg.CGPlayerSendEquip;
import com.stone.actor.player.equip.msg.GCPlayerSendEquipResult;
import com.stone.actor.system.ActorSystem;

public class PlayerSendEquipHandler {
	public void execute(CGPlayerSendEquip msg) {
		// get params
		long senderPlayerId = msg.getSender();
		final long receiverPlayerId = msg.getReceiver();
		final long equipId = msg.getEquipId();
		// get sender
		final PlayerActor senderPlayer = ActorSystem.getInstance().getActor(new ActorId(ActorType.PLAYER, senderPlayerId));
		if (senderPlayer == null) {
			return;
		}
		if (!senderPlayer.getPlayerEquipModule().hasSuchEquip(equipId)) {
			return;
		}
		// make actor call 有以下2种方法可以选择:
		// 1. 使用直接给actor添加匿名调用的方式, 但是这样的代码负责度高，不易阅读
		// 2. 使用actor暴露良好接口的方法，把具体实现包装在actor内部
		// get receiver
		final PlayerActor receiverPlayer = ActorSystem.getInstance().getActor(new ActorId(ActorType.PLAYER, receiverPlayerId));
		IActorFuture<Boolean> future = receiverPlayer.put(new IActorCall<Boolean>() {
			@Override
			public Boolean execute() {
				boolean succeed = receiverPlayer.getPlayerEquipModule().addEquip(equipId);
				return succeed;
			}
		});
		// get result
		// 1. 使用添加结果监听器的方法
		// 2. 使用同步等待结果的方法
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

			@Override
			public IActorId getTarget() {
				return senderPlayer.getActorId();
			}
		});

	}
}
