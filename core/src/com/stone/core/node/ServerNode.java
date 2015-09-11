package com.stone.core.node;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.mina.core.service.IoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.codec.GameCodecFactory;
import com.stone.core.codec.IMessageFactory;
import com.stone.core.concurrent.annotation.GuardedByUnit;
import com.stone.core.concurrent.annotation.ThreadSafeUnit;
import com.stone.core.config.ConfigUtil;
import com.stone.core.config.ServerConfig;
import com.stone.core.net.ServerIoProcessor;
import com.stone.core.node.service.IActorSystem;
import com.stone.core.util.OSUtil;

/**
 * The base stone node;
 * <p>
 * This node contains an main {@link ServerIoProcessor} for node's net service;
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public class ServerNode implements IStoneNode {
	private Logger logger = LoggerFactory.getLogger(ServerNode.class);
	protected String nodeName;
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	protected Map<String, ServerIoProcessor> ioProcessors = new ConcurrentHashMap<String, ServerIoProcessor>();
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	protected Map<String, IActorSystem> systems = new ConcurrentHashMap<String, IActorSystem>();
	/** main io processor */
	protected ServerIoProcessor mainIoProcessor;
	/** server config */
	protected ServerConfig config;
	/** hooks */
	@GuardedByUnit(whoCareMe = "CopyOnWriteArrayList")
	private List<IShutdownHook> hooks = new CopyOnWriteArrayList<IShutdownHook>();
	@GuardedByUnit(whoCareMe = "volatile")
	protected volatile boolean terminated = true;

	protected ServerNode() {
		// shutdown hook
		this.addShutdownHook(new IShutdownHook() {
			@Override
			public void run() {
				shutdown();
			}
		});
	};

	@Override
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	/**
	 * Add safe debug work follow;
	 */
	protected void addSafeDebugWorkFolow() {
		// if windows, add daemon thread wait to safe shutdown
		if (OSUtil.isWindowsOS()) {
			Thread winDeamon = new Thread() {
				@Override
				public void run() {
					try {
						System.in.read();
						// send exit signal
						System.exit(0);
					} catch (IOException e) {
						logger.error("Wait shutdown error", e);
					}
				}
			};
			winDeamon.setDaemon(true);
			winDeamon.setName("WindowDebugGuarder");
			winDeamon.start();
		}
	}

	@Override
	public String getNodeName() {
		return nodeName;
	}

	@Override
	public void addIoProcessor(String name, ServerIoProcessor ioProcessor) {
		this.ioProcessors.put(name, ioProcessor);
	}

	@Override
	public void registerActorSystem(String name, IActorSystem system) {
		systems.put(name, system);
	}

	@Override
	public void unRegisterActorSystem(String name, IActorSystem system) {
		systems.remove(name);
	}

	@Override
	public void startup() throws IOException {
		// start the io processor
		for (Entry<String, ServerIoProcessor> processorEntry : this.ioProcessors.entrySet()) {
			// init and start
			processorEntry.getValue().startup();
			logger.info("ServerIoProcessor: " + processorEntry.getKey() + " started.");
		}
		terminated = false;
		// add debug flow
		addSafeDebugWorkFolow();
	}

	@Override
	public void shutdown() {
		if (terminated) {
			return;
		}
		// shutdown the io processor
		for (Entry<String, ServerIoProcessor> processorEntry : this.ioProcessors.entrySet()) {
			// shutdown
			processorEntry.getValue().shutdown();
			logger.info("ServerIoProcessor: " + processorEntry.getKey() + " shutdown.");
		}
		this.ioProcessors.clear();
		// shutdown the service
		for (Entry<String, IActorSystem> systemEntry : this.systems.entrySet()) {
			systemEntry.getValue().shutdown();
			logger.info("IStoneSystem: " + systemEntry.getKey() + " shutdown.");
		}
		this.systems.clear();
		terminated = true;
	}

	@Override
	public void init(ServerConfig config, IoHandler ioHandler, IMessageFactory messageFactory) throws Exception {
		this.config = config;
		mainIoProcessor = new ServerIoProcessor(config.getBindIp(), config.getPort(), ioHandler, new GameCodecFactory(messageFactory));
		// add processor
		this.addIoProcessor("mainProcessor", mainIoProcessor);
		// add hook
		for (final IShutdownHook eachHook : this.hooks) {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					eachHook.run();
				}
			});
		}
	}

	protected void addShutdownHook(IShutdownHook hook) {
		hooks.add(hook);
	}

	/**
	 * Load the config;
	 * 
	 * @param configClass
	 * @param configPath
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T extends ServerConfig> T loadConfig(Class<?> configClass, String configPath) throws Exception {
		@SuppressWarnings("unchecked")
		T config = (T) configClass.newInstance();
		ConfigUtil.loadJsConfig(config, configPath);
		return config;
	}
}
