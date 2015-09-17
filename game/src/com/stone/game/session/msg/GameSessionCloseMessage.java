package com.stone.game.session.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.msg.BaseCAMessage;
import com.stone.core.msg.MessageParseException;
import com.stone.core.session.BaseActorSession;

/**
 * Game session close message;
 * 
 * @author crazyjohn
 *
 */
public class GameSessionCloseMessage extends BaseCAMessage {
	private Logger logger = LoggerFactory.getLogger(GameSessionCloseMessage.class);

	public GameSessionCloseMessage(BaseActorSession sessionInfo) {
		this.session = sessionInfo;
	}

	@Override
	public void execute() throws MessageParseException {
		logger.info(String.format("Session closed: %s", this.session));
	}

	@Override
	protected boolean readBody() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean writeBody() {
		throw new UnsupportedOperationException();
	}

}
