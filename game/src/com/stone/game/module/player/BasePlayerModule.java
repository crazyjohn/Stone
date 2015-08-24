package com.stone.game.module.player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BasePlayerModule implements IPlayerModule {
	protected Player player;
	/** logger */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public BasePlayerModule(Player player) {
		this.player = player;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

}
