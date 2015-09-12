package com.stone.core.node.slave;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;

public class SlaveMaster extends UntypedActor {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onReceive(Object message) throws Exception {
		logger.info("Received msg:" + message);
	}

}
