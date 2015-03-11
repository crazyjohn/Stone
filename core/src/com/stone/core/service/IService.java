package com.stone.core.service;

import java.io.IOException;

import javax.script.ScriptException;

/**
 * The service interface;
 * 
 * @author crazyjohn
 *
 */
public interface IService {
	/**
	 * Init;
	 * 
	 * @throws ScriptException
	 * @throws IOException
	 */
	public void init() throws ScriptException, IOException;

	/**
	 * Start;
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException;

	/**
	 * Stop;
	 */
	public void shutdown();
}
