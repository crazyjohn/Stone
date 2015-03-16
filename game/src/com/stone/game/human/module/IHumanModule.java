package com.stone.game.human.module;

import com.stone.db.entity.HumanEntity;
import com.stone.game.human.Human;

/**
 * The human module;
 * 
 * @author crazyjohn
 *
 */
public interface IHumanModule {
	/**
	 * Load module data;
	 * 
	 * @param entity
	 */
	public void onLoad(HumanEntity entity);

	/**
	 * Persistence module data;
	 * 
	 * @param entity
	 */
	public void onPersistence(HumanEntity entity);

	/**
	 * Get the host human;
	 * 
	 * @return
	 */
	public Human getOwner();

}
