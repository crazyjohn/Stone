package com.i4joy.akka.kok.king;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class King extends UntypedActor {

	protected final Log logger = LogFactory.getLog(getClass());
	private ActorRef dbMaster;

	public static Props getProps() {
		return Props.create(King.class);
	}

	public King() {
		dbMaster = getContext().actorOf(DBMaster.getProps());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		
	}

}
