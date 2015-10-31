package com.stone.core.node;

import org.apache.mina.core.service.IoHandler;

import com.stone.core.codec.IMessageFactory;
import com.stone.core.config.ServerConfig;
import com.stone.core.lifecircle.ILifeCircle;
import com.stone.core.net.ServerIoProcessor;
import com.stone.core.node.system.IActorSystem;

/**
 * The stone node;
 * 
 * @author crazyjohn
 *
 */
public interface IServerNode extends ILifeCircle {

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
	public void registerIoProcessor(String name, ServerIoProcessor ioProcessor);

	/**
	 * Register a actor system;
	 * 
	 * @param name
	 * @param system
	 */
	public void registerActorSystem(String name, IActorSystem system);

	/**
	 * Unregister the service;
	 * 
	 * @param name
	 * @param system
	 */
	public void unRegisterActorSystem(String name, IActorSystem system);

	/**
	 * Init the node;
	 * 
	 * @param config
	 *            the server node config;
	 * @param ioHandler
	 *            the io handler;
	 * @param messageFactory
	 *            the message factory;
	 * @throws Exception
	 */
	public void init(ServerConfig config, IoHandler ioHandler, IMessageFactory messageFactory) throws Exception;

}
