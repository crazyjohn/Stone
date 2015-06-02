package com.stone.core.net;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.stone.core.service.IService;

/**
 * The net io processor;
 * 
 * @author crazyjohn
 *
 */
public class ServerIoProcessor implements IService {
	/** Acceptor */
	private IoAcceptor acceptor;
	/** port */
	private int port;
	/** bindIp */
	private String bindIp;
	/** ioHandler */
	private IoHandler ioHandler;
	/** codec factory */
	private ProtocolCodecFactory codecFactory;

	public ServerIoProcessor(String bindIp, int port, IoHandler ioHandler, ProtocolCodecFactory codecFactory) {
		acceptor = new NioSocketAcceptor();
		this.bindIp = bindIp;
		this.port = port;
		this.ioHandler = ioHandler;
		this.codecFactory = codecFactory;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() throws IOException {
		acceptor.setHandler(ioHandler);
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(codecFactory));
		acceptor.bind(new InetSocketAddress(this.bindIp, this.port));
	}

	@Override
	public void shutdown() {
		// unbind
		acceptor.unbind();
		// dispose
		acceptor.dispose();
	}

}
