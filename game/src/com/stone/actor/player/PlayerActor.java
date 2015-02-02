package com.stone.actor.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private long playerId;
	/** logger */
	private Logger logger = LoggerFactory.getLogger(PlayerActor.class);

	public PlayerActor(long playerId) {
		// init modules
		initModules();
	}

	private void initModules() {
		// equip
		this.equipModule = new PlayerEquipModule(this);
	}

	public PlayerEquipModule getPlayerEquipModule() {
		return equipModule;
	}

	public void sendMessage(IActorMessage msg) {
		logger.info(String.format("Send message: %s", msg.toString()));
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
		return playerId;
	}

}
