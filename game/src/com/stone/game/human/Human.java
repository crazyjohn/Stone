package com.stone.game.human;

import com.stone.game.player.Player;
import com.stone.game.player.module.item.HumanItemModule;

/**
 * 游戏角色业务对象;
 * 
 * @author crazyjohn
 *
 */
public class Human {
	/** item module */
	protected HumanItemModule itemModule;
	/** human guid */
	private long guid;
	private Player player;

	public Human() {
		// init
		initManager();
	}

	private void initManager() {
		// init item module
		itemModule = new HumanItemModule(this);
	}

	public long getGuid() {
		return guid;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
