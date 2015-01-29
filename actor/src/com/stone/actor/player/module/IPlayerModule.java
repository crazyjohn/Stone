package com.stone.actor.player.module;

import com.stone.actor.player.PlayerActor;

public interface IPlayerModule {

	public PlayerActor getPlayer();

	public void onPlayerLogin();

	public void onPlayerLogout();
}
