package com.stone.core.node.master;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.service.IoHandler;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import com.stone.core.codec.GameCodecFactory;
import com.stone.core.codec.IMessageFactory;
import com.stone.core.config.ServerConfig;
import com.stone.core.config.master.MasterConfig;
import com.stone.core.config.slave.MasterAddress;
import com.stone.core.msg.ProtobufMessageFactory;
import com.stone.core.net.ServerIoProcessor;
import com.stone.core.node.CommonServerInfo;
import com.stone.core.node.ServerNode;
import com.stone.core.node.slave.SlaveIoHandler;
import com.stone.core.node.slave.SlaveMaster;

public class MasterServerNode extends ServerNode implements IMasterServerNode {
	protected Map<String, CommonServerInfo> slaveServers = new HashMap<String, CommonServerInfo>();
	protected ActorSystem proxySystem = ActorSystem.create("SlaveSystem");

	@Override
	public CommonServerInfo getSlaveServer(String serverName) {
		return slaveServers.get(serverName);
	}

	@Override
	public void init(ServerConfig config, IoHandler ioHandler, IMessageFactory messageFactory) throws Exception {
		if (config instanceof MasterConfig) {
			boolean result = initMasterIoProcessor((MasterConfig) config);
			if (!result) {
				throw new IllegalStateException("Init master processor failed");
			}
		}
		super.init(config, ioHandler, messageFactory);
	}

	protected boolean initMasterIoProcessor(MasterConfig config) {
		List<MasterAddress> addresses = config.getAllMasterAddresses();

		for (MasterAddress address : addresses) {
			ActorRef slaveProxy = proxySystem.actorOf(Props.create(SlaveMaster.class), "SlaveProxy-" + address.getMasterName());
			ServerIoProcessor ioProcessor = new ServerIoProcessor(address.getHost(), address.getPort(), new SlaveIoHandler(slaveProxy),
					new GameCodecFactory(new ProtobufMessageFactory()));
			this.addIoProcessor(address.getMasterName(), ioProcessor);
		}
		return true;
	}

}
