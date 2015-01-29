package com.stone.actor.player.equip.msg;

import com.stone.actor.message.IActorMessage;

public class CGPlayerSendEquip implements IActorMessage{
	private long sender;
	private long receiver;
	private long equipId;

	public long getSender() {
		return sender;
	}

	public void setSender(long sender) {
		this.sender = sender;
	}

	public long getReceiver() {
		return receiver;
	}

	public void setReceiver(long receiver) {
		this.receiver = receiver;
	}

	public long getEquipId() {
		return equipId;
	}

	public void setEquipId(long equipId) {
		this.equipId = equipId;
	}

}
