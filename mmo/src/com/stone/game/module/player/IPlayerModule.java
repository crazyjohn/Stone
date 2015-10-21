package com.stone.game.module.player;

import com.stone.game.module.IGameModule;

/**
 * The player module;
 * 
 * @author crazyjohn
 *
 */
public interface IPlayerModule extends IGameModule{

	/**
	 * Get the player;
	 * 
	 * @return
	 */
	public GamePlayer getPlayer();
}
