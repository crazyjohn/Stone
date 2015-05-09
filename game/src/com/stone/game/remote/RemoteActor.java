package com.stone.game.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.UntypedActor;

import com.google.protobuf.Message;
import com.googlecode.protobuf.format.JsonFormat;

/**
 * The remote actor;
 * 
 * @author crazyjohn
 *
 */
public class RemoteActor extends UntypedActor {
	/** loggers */
	protected static Logger logger = LoggerFactory.getLogger(RemoteActor.class);

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof String) {
			logger.info(msg.toString());
		} else if (msg instanceof Message) {
			logger.info(JsonFormat.printToString((Message) msg));
		}
	}

}
