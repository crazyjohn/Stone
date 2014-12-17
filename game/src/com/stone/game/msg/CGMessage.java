package com.stone.game.msg;

import com.stone.core.msg.ISessionMessage;
import com.stone.game.human.Human;

/**
 * client和GameServer通信消息接口;
 * 
 * @author crazyjohn
 *
 */
public interface CGMessage extends ISessionMessage {
	public Human getHuman();
}
