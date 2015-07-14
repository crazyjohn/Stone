package com.stone.core.net;

import java.io.IOException;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.stone.core.lifecircle.ILifeCircle;

public class ServerProcessTest {

	public static void main(String[] args) throws IOException {
		ILifeCircle serverProcess = new ServerIoProcessor("0.0.0.0", 8888, new IoHandler() {

			@Override
			public void sessionOpened(IoSession session) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void sessionCreated(IoSession session) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void sessionClosed(IoSession session) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void messageSent(IoSession session, Object message) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void messageReceived(IoSession session, Object message) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void inputClosed(IoSession session) throws Exception {
				// TODO Auto-generated method stub

			}

			@Override
			public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
				// TODO Auto-generated method stub

			}
		}, new ProtocolCodecFactory() {

			@Override
			public ProtocolEncoder getEncoder(IoSession session) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ProtocolDecoder getDecoder(IoSession session) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
		});
		serverProcess.startup();
		serverProcess.shutdown();
	}

}
