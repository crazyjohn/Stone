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
	 * 
	 * @throws Exception
	 */
	public void heartBeat() throws Exception;

	/**
	 * Start up;
	 */
	public void startup();

	/**
	 * Shutdown;
	 * 
	 * @throws Exception
	 */
	public void shutdown() throws Exception;

}
