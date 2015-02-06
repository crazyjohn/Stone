package com.stone.game.player.module;

import com.stone.game.human.Human;

public abstract class BaseHumanModule implements IHumanModule {
	protected Human human;

	public BaseHumanModule(Human human) {
		this.human = human;
	}

	@Override
	public Human getOwner() {
		return human;
	}

}
