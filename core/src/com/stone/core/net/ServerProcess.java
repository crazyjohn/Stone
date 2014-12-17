package com.stone.core.net;

import com.stone.core.config.IConfig;
import com.stone.core.processor.IDispatcher;
import com.stone.core.service.IService;
/**
 * 服务器网络io进程;
 * @author crazyjohn
 *
 */
public class ServerProcess implements IService {
	protected IoAcceptor accepter;
	
	public ServerProcess(String bindIp, int port, IDispatcher dispatcher) {
		
	}
	
	@Override
	public void init(IConfig config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
