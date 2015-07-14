package com.stone.game.module.player;

public abstract class BasePlayerModule implements IPlayerModule {
	protected Player player;

	public BasePlayerModule(Player player) {
		this.player = player;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

}
