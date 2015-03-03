package com.stone.game.human;


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
