package com.stone.gate.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.stone.core.msg.ProtobufMessage;
import com.stone.proto.MessageTypes.MessageType;
import com.stone.proto.Servers.Register;

public class GateProxyMaster extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private ActorRef gateMaster;

	public GateProxyMaster(ActorRef gateMaster) {
		this.gateMaster = gateMaster;
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof ProtobufMessage) {
			ProtobufMessage protobufMessage = (ProtobufMessage) msg;
			if (protobufMessage.getType() == MessageType.SERVER_REGISTER_REQUEST_VALUE) {
				Register.Builder resiger = protobufMessage.parseBuilder(Register.newBuilder());
				gateMaster.tell(resiger, getSelf());
			}
			logger.info("GateProxyMaster received msg: " + protobufMessage);
		} else {
			unhandled(msg);
		}

	}

}
