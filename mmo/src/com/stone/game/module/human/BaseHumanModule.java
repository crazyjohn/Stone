package com.stone.game.module.human;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.db.entity.HumanEntity;
import com.stone.game.human.Human;
import com.stone.game.module.player.GamePlayer;

/**
 * The base human module;
 * 
 * @author crazyjohn
 *
 */
public abstract class BaseHumanModule implements IHumanModule {
	/** host human */
	protected Human human;
	protected GamePlayer player;
	/** logger */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	protected boolean open = true;

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
		// do nothing

	}

	@Override
	public void onPersistence(HumanEntity humanEntity) {
		// do nothing

	}

	@Override
	public void close() {
		this.open = false;
	}

	@Override
	public boolean isOpen() {
		return this.open;
	}

	@Override
	public void open() {
		this.open = true;
	}

}
