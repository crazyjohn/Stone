package com.stone.core.node;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.stone.core.concurrent.annotation.GuardedByUnit;
import com.stone.core.concurrent.annotation.ThreadSafeUnit;
import com.stone.core.net.ServerIoProcessor;

/**
 * The base stone node;
 * 
 * @author crazyjohn
 *
 */
@ThreadSafeUnit
public class StoneNode implements IStoneNode {
	protected String nodeName;
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	protected Map<String, ServerIoProcessor> ioProcessors = new ConcurrentHashMap<String, ServerIoProcessor>();
	@GuardedByUnit(whoCareMe = "ConcurrentHashMap")
	protected Map<String, IStoneService> services = new ConcurrentHashMap<String, IStoneService>();

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
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}
}
