package com.stone.actor.player.module;

import com.stone.actor.player.PlayerActor;

/**
 * 玩家模块接口;
 * 
 * @author crazyjohn
 *
 */
public interface IPlayerModule {

	/**
	 * 获取对应玩家;
	 * 
	 * @return
	 */
	public PlayerActor getPlayer();

	/**
	 * 登陆回调;
	 */
	public void onPlayerLogin();

	/**
	 * 登出回调;
	 */
	public void onPlayerLogout();
}
