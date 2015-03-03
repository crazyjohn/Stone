package com.stone.game.player;

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
