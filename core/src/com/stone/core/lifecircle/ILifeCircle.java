package com.stone.core.lifecircle;

import java.io.IOException;

import com.stone.core.node.IShutdownHook;

/**
 * LifeCircle;
 * 
 * @author crazyjohn
 *
 */
public interface ILifeCircle {
	/**
	 * Start the node;
	 * 
	 * @throws IOException
	 */
	public void startup() throws IOException;

	/**
	 * Shutdown the node;
	 */
	public void shutdown();

	/**
	 * Add shutdown hook;
	 * 
	 * @param hook
	 */
	public void addShutdownHook(IShutdownHook hook);
}
