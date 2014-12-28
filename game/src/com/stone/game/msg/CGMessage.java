package com.stone.game.msg;

import com.stone.core.msg.ISessionMessage;
import com.stone.game.player.Player;
import com.stone.game.session.GamePlayerSession;

/**
 * client和GameServer通信消息接口;
 * 
 * @author crazyjohn
 *
 */
public interface CGMessage extends ISessionMessage<GamePlayerSession> {
	public Player getPlayer();
}
