package com.stone.game.module.human;

/**
 * The stateful module;
 * <p>
 * When such module in close state, it dose not handle any message;
 * 
 * @author crazyjohn
 *
 */
public interface IStatefulModule {

	/**
	 * Open the module;
	 */
	public void open();

	/**
	 * Close the module;
	 */
	public void close();

	/**
	 * Is the module in open state?
	 * 
	 * @return
	 */
	public boolean isOpen();
}
