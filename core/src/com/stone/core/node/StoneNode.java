package com.stone.core.node;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.mina.core.service.IoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stone.core.codec.GameCodecFactory;
import com.stone.core.codec.IMessageFactory;
import com.stone.core.concurrent.annotation.GuardedByUnit;
import com.stone.core.concurrent.annotation.ThreadSafeUnit;
import com.stone.core.config.ServerConfig;
import com.stone.core.net.ServerIoProcessor;

/**
 * The base stone node;
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public class StoneNode implements IStoneNode {
	private Logger logger = LoggerFactory.getLogger(StoneNode.class);
	protected String nodeName;
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	protected Map<String, ServerIoProcessor> ioProcessors = new ConcurrentHashMap<String, ServerIoProcessor>();
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	protected Map<String, IStoneService> services = new ConcurrentHashMap<String, IStoneService>();
	/** main io processor */
	protected ServerIoProcessor mainIoProcessor;
	protected ServerConfig config;

	@Override
	public void setName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
	public String getName() {
		return nodeName;
	}

	@Override
	public void addIoProcessor(String name, ServerIoProcessor ioProcessor) {
		this.ioProcessors.put(name, ioProcessor);
	}

	@Override
	public void registerService(String name, IStoneService service) {
		services.put(name, service);
	}

	@Override
	public void unRegisterService(String name, IStoneService service) {
		services.remove(name);
	}

	@Override
	public void start() throws IOException {
		// start the io processor
		for (Entry<String, ServerIoProcessor> processorEntry : this.ioProcessors.entrySet()) {
			// init and start
			processorEntry.getValue().init();
			processorEntry.getValue().start();
			logger.info("ServerIoProcessor: " + processorEntry.getKey() + " started.");
		}
	}

	@Override
	public void shutdown() {
		// shutdown the io processor
		for (Entry<String, ServerIoProcessor> processorEntry : this.ioProcessors.entrySet()) {
			// shutdown
			processorEntry.getValue().shutdown();
			logger.info("ServerIoProcessor: " + processorEntry.getKey() + " shutdown.");
		}
	}

	@Override
	public void init(ServerConfig config, IoHandler ioHandler, IMessageFactory messageFactory) throws Exception {
		this.config = config;
		mainIoProcessor = new ServerIoProcessor(config.getBindIp(), config.getPort(), ioHandler, new GameCodecFactory(messageFactory));
		// add processor
		this.addIoProcessor("mainProcessor", mainIoProcessor);
	}
}
