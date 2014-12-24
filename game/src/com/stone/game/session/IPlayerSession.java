package com.stone.game.session;

import com.stone.core.session.ISession;
import com.stone.game.player.Player;

/**
 * 玩家回话信息接口;
 * 
 * @author crazyjohn
 *
 */
public interface IPlayerSession extends ISession {

	/**
	 * 获取绑定玩家;
	 * 
	 * @return
	 */
	public Player getPlayer();

	/**
	 * 设置绑定玩家;
	 * 
	 * @param player
	 */
	public void setPlayer(Player player);
}
