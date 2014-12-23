package com.stone.core.service;

import java.io.IOException;

import com.stone.core.config.IConfig;

/**
 * 服务接口;
 * 
 * @author crazyjohn
 *
 */
public interface IService {
	/**
	 * 初始化;
	 * 
	 * @param config
	 */
	public void init(IConfig config);

	/**
	 * 启动服务;
	 */
	public void start() throws IOException;

	/**
	 * 停止服务;
	 */
	public void stop();
}
