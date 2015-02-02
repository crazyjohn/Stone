package com.stone.game.human;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.stone.core.lock.ILockable;

/**
 * 游戏角色业务对象;
 * 
 * @author crazyjohn
 *
 */
public class Human implements ILockable {
	private Lock lock;

	public Human() {
		// init
		initManager();
		lock = new ReentrantLock();
	}

	private void initManager() {
		// init all managers
	}

	@Override
	public ILockable lock() {
		lock.lock();
		return this;
	}

	@Override
	public void unlock() {
		lock.unlock();
	}

	public long getGuid() {
		// TODO Auto-generated method stub
		return 0;
	}

}
