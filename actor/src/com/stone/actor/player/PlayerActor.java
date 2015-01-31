package com.stone.actor.player;

import com.stone.actor.BaseActor;
import com.stone.actor.call.IActorCall;
import com.stone.actor.future.ActorFuture;
import com.stone.actor.future.IActorFuture;
import com.stone.actor.message.IActorMessage;
import com.stone.actor.player.equip.PlayerEquip;
import com.stone.actor.player.equip.PlayerEquipModule;

/**
 * 玩家活动对象;
 * 
 * @author crazyjohn
 *
 */
public class PlayerActor extends BaseActor {
	/** equip module */
	private PlayerEquipModule equipModule;

	public PlayerEquipModule getPlayerEquipModule() {
		return equipModule;
	}

	public void sendMessage(IActorMessage resultMsg) {
		// TODO Auto-generated method stub

	}

	public IActorFuture<PlayerEquip> getPlayerEquip(final long equipId) {
		final IActorFuture<PlayerEquip> future = new ActorFuture<PlayerEquip>();
		this.put(new IActorCall<PlayerEquip>() {
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
