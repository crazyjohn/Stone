package com.stone.actor.player;

import com.stone.actor.BaseActor;
import com.stone.actor.call.IActorCall;
import com.stone.actor.future.ActorFuture;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.message.IActorMessage;
import com.stone.actor.player.equip.PlayerEquip;
import com.stone.actor.player.equip.PlayerEquipModule;

public class PlayerActor extends BaseActor {
	private PlayerEquipModule equipModule;

	public PlayerEquipModule getPlayerEquipModule() {
		return equipModule;
	}

	public void sendMessage(IActorMessage resultMsg) {
		// TODO Auto-generated method stub

	}

	public IActorFuture<PlayerEquip> getPlayerEquip(final long equipId) {
		final IActorFuture<PlayerEquip> future = new ActorFuture<PlayerEquip>();
		this.call(new IActorCall<PlayerEquip>() {
			@Override
			public PlayerEquip execute() {
				PlayerEquip equip = equipModule.getEquipById(equipId);
				future.setResult(equip);
				return equip;
			}
		});
		return future;
	}

	public long getId() {
		// TODO Auto-generated method stub
		return 0l;
	}

}
