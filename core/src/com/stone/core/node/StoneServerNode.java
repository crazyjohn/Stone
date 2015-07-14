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
import com.stone.core.util.OSUtil;

/**
 * The base stone node;
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public class StoneServerNode implements IStoneNode {
	private Logger logger = LoggerFactory.getLogger(StoneServerNode.class);
	protected String nodeName;
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	protected Map<String, ServerIoProcessor> ioProcessors = new ConcurrentHashMap<String, ServerIoProcessor>();
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	protected Map<String, IStoneActorService> services = new ConcurrentHashMap<String, IStoneActorService>();
	/** main io processor */
	protected ServerIoProcessor mainIoProcessor;
	/** server config */
	protected ServerConfig config;
	/** hooks */
	@GuardedByUnit(whoCareMe = "CopyOnWriteArrayList")
	private List<IShutdownHook> hooks = new CopyOnWriteArrayList<IShutdownHook>();
	@GuardedByUnit(whoCareMe = "volatile")
	protected volatile boolean terminated = true;

	public StoneServerNode() {
		// shutdown hook
		this.addShutdownHook(new IShutdownHook() {
			@Override
			public void run() {
				shutdown();
			}
		});
	}

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
	public void registerService(String name, IStoneActorService service) {
		services.put(name, service);
	}

	@Override
	public void unRegisterService(String name, IStoneActorService service) {
		services.remove(name);
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
		for (Entry<String, IStoneActorService> serviceEntry : this.services.entrySet()) {
			serviceEntry.getValue().shutdown();
			logger.info("IStoneService: " + serviceEntry.getKey() + " shutdown.");
		}
		this.services.clear();
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

	public void addShutdownHook(IShutdownHook hook) {
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
	public <T extends ServerConfig> T loadConfig(Class<?> configClass, String configPath) throws Exception {
		@SuppressWarnings("unchecked")
		T config = (T) configClass.newInstance();
		ConfigUtil.loadJsConfig(config, configPath);
		return config;
	}
}
