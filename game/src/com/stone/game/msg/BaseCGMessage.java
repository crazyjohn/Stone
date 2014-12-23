package com.stone.game.msg;

import org.apache.mina.core.session.IoSession;

import com.stone.core.msg.BaseMessage;
import com.stone.game.human.Human;

/**
 * 基础CG消息;
 * 
 * @author crazyjohn
 *
 */
public class BaseCGMessage extends BaseMessage implements CGMessage {

	@Override
	public IoSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Human getHuman() {
		// TODO Auto-generated method stub
		return null;
	}

}
