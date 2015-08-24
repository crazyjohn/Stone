package com.stone.game.module.human;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.db.entity.HumanEntity;
import com.stone.game.human.Human;
import com.stone.game.module.player.Player;

/**
 * The base human module;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseHumanModule implements IHumanModule {
	/** host human */
	protected Human human;
	protected Player player;
	/** logger */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public BaseHumanModule(Human human) {
		this.human = human;
		this.player = human.getPlayer();
	}

	@Override
	public Human getOwner() {
		return human;
	}

	@Override
	public void onLoad(HumanEntity humanEntity) {
		// TODO do nothing

	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		// TODO do nothing

	}

}
