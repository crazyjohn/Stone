package com.stone.game.handler;

import com.google.protobuf.Message.Builder;

public interface IProtobufHandler<Message extends Builder> {
	public void execute(Message message);

	public short getMessageType();
}
