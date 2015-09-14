package com.stone.gate.actor;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import com.stone.proto.Servers.Register;
import com.stone.proto.Servers.Register.Builder;

public class GateMaster extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Map<String, ActorRef> proxys = new HashMap<String, ActorRef>();
	@Override
	public void onReceive(Object msg) throws Exception {
		// FIXME: crazyjohn forward the msg to game
		if (msg instanceof Register.Builder) {
			Register.Builder register = (Builder) msg;
			proxys.put(register.getInfo().getName(), getSender());
		}
		logger.info("GateMaster received msg: " + msg);
	}

}
