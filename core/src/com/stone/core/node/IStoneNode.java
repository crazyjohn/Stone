package com.stone.core.node;

import org.apache.mina.core.service.IoHandler;

import com.stone.core.codec.IMessageFactory;
import com.stone.core.config.ServerConfig;
import com.stone.core.lifecircle.ILifeCircle;
import com.stone.core.net.ServerIoProcessor;

/**
 * The stone node;
 * 
 * @author crazyjohn
 *
 */
public interface IStoneNode extends ILifeCircle {

	/**
	 * Set node name;
	 * 
	 * @param nodeName
	 */
	public void setNodeName(String nodeName);

	/**
	 * Get node name;
	 * 
	 * @return
	 */
	public String getNodeName();

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
	public void registerService(String name, IStoneActorService service);

	/**
	 * Unregister the service;
	 * 
	 * @param name
	 * @param service
	 */
	public void unRegisterService(String name, IStoneActorService service);

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

}
