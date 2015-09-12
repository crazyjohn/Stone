package com.stone.core.node;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.stone.core.config.MasterAddress;
import com.stone.core.config.SlaveServerConfig;
import com.stone.core.node.info.CommonServerInfo;
import com.stone.core.node.info.ISlaveServerNode;
import com.stone.proto.MessageTypes.MessageType;
import com.stone.proto.Servers.Register;

public class SlaveServerNode extends ServerNode implements ISlaveServerNode {
	protected Map<String, CommonServerInfo> masterServers = new HashMap<String, CommonServerInfo>();
	protected Map<String, NioSocketConnector> masterConnectors = new HashMap<String, NioSocketConnector>();

	@Override
	public CommonServerInfo getMasterServerInfo(String serverName) {
		return masterServers.get(serverName);
	}

	@Override
	public boolean connectToMasters(SlaveServerConfig config) {
		List<MasterAddress> addresses = config.getAllMasterAddresses();
		for (MasterAddress address : addresses) {
			NioSocketConnector connector = new NioSocketConnector();
			ConnectFuture future = connector.connect(new InetSocketAddress(address.getHost(), address.getPort()));
			future.awaitUninterruptibly();
			IoSession session = future.getSession();
			// register
			CommonServerInfo serverInfo = new CommonServerInfo(session, address.getMasterName());
			serverInfo.sendMessage(MessageType.SERVER_REGISTER_REQUEST_VALUE, Register.newBuilder().setInfo(this.getServerInfo()));
			masterServers.put(address.getMasterName(), serverInfo);
		}
		return true;
	}
}
