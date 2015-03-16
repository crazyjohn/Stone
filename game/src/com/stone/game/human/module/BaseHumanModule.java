package com.stone.game.human.module;

import com.stone.game.human.Human;

public abstract class BaseHumanModule implements IHumanModule {
	/** host human */
	protected Human human;

	public BaseHumanModule(Human human) {
		this.human = human;
	}

	@Override
	public Human getOwner() {
		return human;
	}

}
