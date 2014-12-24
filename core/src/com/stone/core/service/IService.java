package com.stone.core.service;

import java.io.IOException;

import javax.script.ScriptException;

/**
 * 服务接口;
 * 
 * @author crazyjohn
 *
 */
public interface IService {
	/**
	 * 初始化;
	 */
	public void init() throws ScriptException, IOException;

	/**
	 * 启动服务;
	 */
	public void start() throws IOException;

	/**
	 * 停止服务;
	 */
	public void shutdown();
}
