package com.stone.actor.player.equip.msg;

import com.stone.actor.player.msg.BasePlayerActorMessage;

public class GCPlayerSendEquipResult extends BasePlayerActorMessage {
	private long equipId;
	private long receiverId;
	private boolean sendResult;

	public long getEquipId() {
		return equipId;
	}

	public void setEquipId(long equipId) {
		this.equipId = equipId;
	}

	public long getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(long receiverId) {
		this.receiverId = receiverId;
	}

	public boolean isSendResult() {
		return sendResult;
	}

	public void setSendResult(boolean sendResult) {
		this.sendResult = sendResult;
	}

}
