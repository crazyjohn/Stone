package com.stone.core.node;

import java.io.IOException;

import org.apache.mina.core.service.IoHandler;

import com.stone.core.codec.IMessageFactory;
import com.stone.core.config.ServerConfig;
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
	 * Init the node;
	 * 
	 * @param config
	 *            the node config;
	 * @param ioHandler
	 *            io handler;
	 * @param messageFactory
	 * @throws Exception
	 */
	public void init(ServerConfig config, IoHandler ioHandler, IMessageFactory messageFactory) throws Exception;

	/**
	 * Start the node;
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException;

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
