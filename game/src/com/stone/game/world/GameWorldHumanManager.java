package com.stone.game.world;

import com.stone.core.lock.ILockableContainer;
import com.stone.game.human.Human;

/**
 * 游戏世界玩家管理器;
 * 
 * @author crazyjohn
 *
 */
public class GameWorldHumanManager implements ILockableContainer<Long, Human> {

	@Override
	public Human lock(Long key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void unlock(Human value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void put(Long key, Human value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Human get(Long key) {
		// TODO Auto-generated method stub
		return null;
	}

}
