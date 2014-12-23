package com.stone.core.net;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.stone.core.config.IConfig;
import com.stone.core.service.IService;

/**
 * 服务器网络io进程;
 * 
 * @author crazyjohn
 *
 */
public class ServerProcess implements IService {
	/** Acceptor */
	private IoAcceptor acceptor;
	/** port */
	private int port;
	/** bindIp */
	private String bindIp;
	/** io回调处理器 */
	private IoHandler ioHandler;
	/** 编解码工厂 */
	private ProtocolCodecFactory codecFactory;

	public ServerProcess(String bindIp, int port, IoHandler ioHandler,
			ProtocolCodecFactory codecFactory) {
		acceptor = new NioSocketAcceptor();
		this.bindIp = bindIp;
		this.port = port;
		this.ioHandler = ioHandler;
		this.codecFactory = codecFactory;
	}

	@Override
	public void init(IConfig config) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws IOException {
		acceptor.setHandler(ioHandler);
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(codecFactory));
		acceptor.bind(new InetSocketAddress(this.bindIp, this.port));
	}

	@Override
	public void stop() {
		// 解除绑定;
		acceptor.unbind();
		acceptor.dispose();
	}

}
