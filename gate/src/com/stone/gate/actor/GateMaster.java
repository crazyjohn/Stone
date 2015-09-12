package com.stone.gate.actor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;

public class GateMaster extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onReceive(Object msg) throws Exception {
		// FIXME: crazyjohn forward the msg to game
		logger.info("Received msg:" + msg);
	}

}
