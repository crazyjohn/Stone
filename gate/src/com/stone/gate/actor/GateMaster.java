package com.stone.gate.actor;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;

import com.googlecode.protobuf.format.JsonFormat;
import com.stone.core.msg.CGMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.core.msg.ProtobufMessage;
import com.stone.core.msg.server.ServerInternalMessage;
import com.stone.gate.msg.GateSessionCloseMessage;
import com.stone.gate.msg.GateSessionOpenMessage;
import com.stone.proto.MessageTypes.MessageType;
import com.stone.proto.Servers.Register;

public class GateMaster extends UntypedActor {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/** counter */
	private AtomicLong counter = new AtomicLong(0);

	@Override
	public void onReceive(Object msg) throws Exception {
		if (msg instanceof GateSessionOpenMessage) {
			// open session
			GateSessionOpenMessage sessionOpenMsg = (GateSessionOpenMessage) msg;
			onGateSessionOpened(sessionOpenMsg);
		} else if (msg instanceof GateSessionCloseMessage) {
			// close session
			GateSessionCloseMessage sessionClose = (GateSessionCloseMessage) msg;
			onGateSessionClosed(sessionClose);
		} else if (msg instanceof CGMessage) {
			// dispatchToTargetPlayerActor(msg);
		} else if (msg instanceof ServerInternalMessage) {
			onServerInternalMessage((ServerInternalMessage) msg);
		} else {
			unhandled(msg);
		}
	}

	private void onServerInternalMessage(ServerInternalMessage msg) throws MessageParseException {
		ProtobufMessage protoMessage = msg.getRealMessage();
		if (protoMessage.getType() == MessageType.SERVER_REGISTER_REQUEST_VALUE) {
			Register.Builder register = protoMessage.parseBuilder(Register.newBuilder());
			logger.info(JsonFormat.printToString(register.build()));
		}
	}

	private void onGateSessionClosed(GateSessionCloseMessage sessionClose) throws MessageParseException {
		// remove
		sessionClose.execute();
		// forward
		sessionClose.getPlayerActor().forward(sessionClose, getContext());
		// stop the actor
		// FIXME: crazyjohn at first i use the 'context().stop(subActor)' way,
		// but it's not work, so i change to poison way
		sessionClose.getPlayerActor().tell(PoisonPill.getInstance(), getSender());
		getContext().unwatch(sessionClose.getPlayerActor());

	}

	private void onGateSessionOpened(GateSessionOpenMessage sessionOpenMsg) {
		if (sessionOpenMsg.getSession().getActor() == null) {
			ActorRef gatePlayerActor = getContext().actorOf(Props.create(GatePlayerActor.class), "gatePlayerActor" + counter.incrementAndGet());
			// watch this player actor
			getContext().watch(gatePlayerActor);
			sessionOpenMsg.getSession().setActor(gatePlayerActor);
			gatePlayerActor.forward(sessionOpenMsg, getContext());
		} else {
			// invalid, close session
			sessionOpenMsg.getSession().close();
		}
	}
}
