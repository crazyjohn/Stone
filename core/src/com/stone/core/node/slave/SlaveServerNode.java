package com.stone.core.node.slave;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.stone.core.codec.GameCodecFactory;
import com.stone.core.config.slave.MasterAddress;
import com.stone.core.config.slave.SlaveServerConfig;
import com.stone.core.msg.ProtobufMessageFactory;
import com.stone.core.node.CommonServerInfo;
import com.stone.core.node.ServerNode;
import com.stone.proto.MessageTypes.MessageType;
import com.stone.proto.Servers.Register;

public class SlaveServerNode extends ServerNode implements ISlaveServerNode {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	protected Map<String, CommonServerInfo> masterServers = new HashMap<String, CommonServerInfo>();
	protected Map<String, NioSocketConnector> masterConnectors = new HashMap<String, NioSocketConnector>();
	protected ActorSystem slaveSystem = ActorSystem.create("SlaveSystem");

	@Override
	public CommonServerInfo getMasterServerInfo(String serverName) {
		return masterServers.get(serverName);
	}

	@Override
	public boolean connectToMasters(SlaveServerConfig config) {
		List<MasterAddress> addresses = config.getAllMasterAddresses();
		for (MasterAddress address : addresses) {
			// create connector
			NioSocketConnector connector = new NioSocketConnector();
			ActorRef slaveMaster = slaveSystem.actorOf(Props.create(SlaveMaster.class), "SlaveMaster-" + address.getMasterName());
			connector.setHandler(new SlaveIoHandler(slaveMaster));
			connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new GameCodecFactory(new ProtobufMessageFactory())));
			// connect to master
			ConnectFuture future = connector.connect(new InetSocketAddress(address.getHost(), address.getPort()));
			logger.info(String.format("Start to connect %s master node...", address.getMasterName()));
			// crazy?
			future.awaitUninterruptibly();
			logger.info(String.format("Connect to %s succeed.", address.getMasterName()));
			IoSession session = future.getSession();
			// register
			CommonServerInfo serverInfo = new CommonServerInfo(session, address.getMasterName(), address.getServerType());
			logger.info(String.format("Start to register on %s master node...", address.getMasterName()));
			serverInfo.sendMessage(MessageType.SERVER_REGISTER_REQUEST_VALUE, Register.newBuilder().setInfo(this.getServerInfo()));
			masterServers.put(address.getMasterName(), serverInfo);
		}
		return true;
	}
}
