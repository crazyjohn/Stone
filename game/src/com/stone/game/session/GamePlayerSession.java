package com.stone.game.session;

import org.apache.mina.core.session.IoSession;

import com.stone.core.session.BaseSession;
import com.stone.game.player.Player;

/**
 * 游戏玩家回话信息;
 * 
 * @author crazyjohn
 *
 */
public class GamePlayerSession extends BaseSession implements IPlayerSession {
	public GamePlayerSession(IoSession session) {
		super(session);
	}

	protected Player player;

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	@Override
	public String toString() {
		return session.toString();
	}

}
