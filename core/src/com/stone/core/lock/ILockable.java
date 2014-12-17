package com.stone.core.lock;

/**
 * 支持锁定的对象接口;
 * 
 * @author crazyjohn
 *
 */
public interface ILockable {
	/**
	 * 锁定方法;
	 * 
	 * @return
	 */
	public ILockable lock();

	/**
	 * 解锁方法;
	 */
	public void unlock();

}
