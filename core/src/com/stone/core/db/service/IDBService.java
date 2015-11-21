package com.stone.core.db.service;

/**
 * The db service;
 * 
 * @author crazyjohn
 *
 */
public interface IDBService {
	/**
	 * Do heartBeat things;
	 */
	public void heartBeat();

	/**
	 * Start up;
	 */
	public void startup();

	/**
	 * Shutdown;
	 */
	public void shutdown();

}
