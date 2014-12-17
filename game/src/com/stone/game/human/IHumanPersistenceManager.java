package com.stone.game.human;

import com.stone.db.entity.HumanEntity;

/**
 * 玩家持久化管理器接口;
 * @author crazyjohn
 *
 */
public interface IHumanPersistenceManager {
	/**
	 * 加载数据;
	 * @param entity
	 */
	public void onLoad(HumanEntity entity);

	/**
	 * 持久化数据;
	 * @param entity
	 */
	public void onPersistence(HumanEntity entity);

	public Human getOwner();
}
