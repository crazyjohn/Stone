package com.stone.core.lifecircle;

import java.io.IOException;

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

}
