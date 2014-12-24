package com.stone.game.msg;

import com.stone.core.msg.BaseMessage;
import com.stone.core.session.ISession;
import com.stone.game.human.Human;

/**
 * 基础CG消息;
 * 
 * @author crazyjohn
 *
 */
public class BaseCGMessage extends BaseMessage implements CGMessage {
	protected ISession session;
	@Override
	public ISession getSession() {
		return session;
	}

	@Override
	public Human getHuman() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSession(ISession session) {
		this.session = session;
	}

}
