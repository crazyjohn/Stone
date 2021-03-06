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
import com.stone.core.config.ServerConfig;
import com.stone.core.net.ServerIoProcessor;
import com.stone.core.node.system.IActorSystem;
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
public class ServerNode implements IServerNode {
	private Logger logger = LoggerFactory.getLogger(ServerNode.class);
	protected final String nodeName;
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	protected Map<String, ServerIoProcessor> ioProcessors = new ConcurrentHashMap<String, ServerIoProcessor>();
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	protected Map<String, IActorSystem> systems = new ConcurrentHashMap<String, IActorSystem>();
	/** main io processor */
	protected ServerIoProcessor mainIoProcessor;
	/** hooks */
	@GuardedByUnit(whoCareMe = "CopyOnWriteArrayList")
	private List<IShutdownHook> hooks = new CopyOnWriteArrayList<IShutdownHook>();
	@GuardedByUnit(whoCareMe = "volatile")
	protected volatile boolean terminated = true;

	public ServerNode(String nodeName) {
		this.nodeName = nodeName;
		// shutdown hook
		this.addShutdownHook(new IShutdownHook() {
			@Override
			public void run() {
				shutdown();
			}
		});
	};

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
	public void registerIoProcessor(String name, ServerIoProcessor ioProcessor) {
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
		// add hook
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				for (final IShutdownHook eachHook : hooks) {
					eachHook.run();
				}
			}
		});
	}

	@Override
	public void shutdown() {
		logger.info(String.format("Begin to shutdown the %s node ...", nodeName));
		if (terminated) {
			return;
		}
		// shutdown the io processor
		for (Entry<String, ServerIoProcessor> processorEntry : this.ioProcessors.entrySet()) {
			// shutdown
			processorEntry.getValue().shutdown();
			logger.debug("ServerIoProcessor: " + processorEntry.getKey() + " shutdown.");
		}
		this.ioProcessors.clear();
		// shutdown all actor systems
		for (Entry<String, IActorSystem> systemEntry : this.systems.entrySet()) {
			systemEntry.getValue().shutdown();
			logger.debug("IStoneSystem: " + systemEntry.getKey() + " shutdown.");
		}
		this.systems.clear();
		terminated = true;
		logger.info(String.format("Node %s already shutdown.", nodeName));
	}

	@Override
	public void init(ServerConfig config, IoHandler ioHandler, IMessageFactory messageFactory) throws Exception {
		mainIoProcessor = new ServerIoProcessor(config.getBindIp(), config.getPort(), ioHandler, new GameCodecFactory(messageFactory));
		// add processor
		this.registerIoProcessor("mainProcessor", mainIoProcessor);

	}

	protected void addShutdownHook(IShutdownHook hook) {
		hooks.add(hook);
	}
}
