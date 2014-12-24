package com.stone.game;

import com.stone.core.config.IConfig;
import com.stone.core.net.ServerProcess;
import com.stone.core.processor.IDispatcher;
import com.stone.core.service.IService;

/**
 * 游戏服务器;
 * 
 * @author crazyjohn
 *
 */
public class GameServer implements IService {
	protected ServerProcess externalProcess;
	protected IDispatcher mainDispatcher;
	protected IDispatcher dbDispatcher;

	@Override
	public void init(IConfig config) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
