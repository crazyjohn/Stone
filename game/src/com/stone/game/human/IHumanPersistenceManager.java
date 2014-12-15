package com.stone.game.human;

import com.stone.db.entity.HumanEntity;

/**
 * 支持持久化动作的玩家业务管理器接口;
 * 
 * @author crazyjohn
 *
 */
public interface IHumanPersistenceManager {
	/**
	 * 加载接口;
	 * 
	 * @param entity
	 */
	public void onLoad(HumanEntity entity);

	/**
	 * 持久化接口;
	 * 
	 * @param entity
	 */
	public void onPersistence(HumanEntity entity);

	public Human getOwner();
}
