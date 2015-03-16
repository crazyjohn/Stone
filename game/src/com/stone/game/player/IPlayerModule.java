package com.stone.game.player;

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
	public Player getPlayer();
}
