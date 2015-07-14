package com.stone.game.module.human;

import com.stone.game.human.Human;

/**
 * The base human module;
 * 
 * @author crazyjohn
 *
 */
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
