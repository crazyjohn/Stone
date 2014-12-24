package com.stone.game.msg;

import com.stone.core.msg.BaseMessage;
import com.stone.game.human.Human;
import com.stone.game.session.GamePlayerSession;

/**
 * 基础CG消息;
 * 
 * @author crazyjohn
 *
 */
public class BaseCGMessage extends BaseMessage implements CGMessage {
	protected GamePlayerSession session;

	@Override
	public GamePlayerSession getSession() {
		return session;
	}

	@Override
	public void setSession(GamePlayerSession session) {
		this.session = session;
	}

	@Override
	public Human getHuman() {
		return session.getPlayer().getHuman();
	}

}
