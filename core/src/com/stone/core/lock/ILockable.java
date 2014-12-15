package com.stone.core.lock;

/**
 * 支持锁定的接口;
 * 
 * @author crazyjohn
 *
 */
public interface ILockable {
	/**
	 * 锁定自己;
	 * 
	 * @return
	 */
	public ILockable lock();

	/**
	 * 解鎖;
	 */
	public void unlock();

}
