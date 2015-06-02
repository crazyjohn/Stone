package com.stone.core.node;

import com.stone.core.net.ServerIoProcessor;

/**
 * The stone node;
 * 
 * @author crazyjohn
 *
 */
public interface IStoneNode {

	/**
	 * Set node name;
	 * 
	 * @param nodeName
	 */
	public void setName(String nodeName);

	/**
	 * Get node name;
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * Add io processor;
	 * 
	 * @param name
	 * @param ioProcessor
	 */
	public void addIoProcessor(String name, ServerIoProcessor ioProcessor);

	/**
	 * Register a service;
	 * 
	 * @param name
	 * @param service
	 */
	public void registerService(String name, IStoneService service);

	/**
	 * Unregister the service;
	 * 
	 * @param name
	 * @param service
	 */
	public void unRegisterService(String name, IStoneService service);

	/**
	 * Start the node;
	 */
	public void start();

	/**
	 * Shutdown the node;
	 */
	public void shutdown();
}
