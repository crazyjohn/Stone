package com.stone.actor.player.equip.msg;

import com.stone.actor.message.BasePlayerActorMessage;

public class CGSeeOtherPlayerEquip extends BasePlayerActorMessage {
	private long playerId;
	private long equipId;

	public long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}

	public long getEquipId() {
		return equipId;
	}

	public void setEquipId(long equipId) {
		this.equipId = equipId;
	}

}
