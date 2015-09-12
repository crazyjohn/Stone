package com.stone.core.node.master;

import java.util.HashMap;
import java.util.Map;

import com.stone.core.node.CommonServerInfo;
import com.stone.core.node.ServerNode;

public class MasterServerNode extends ServerNode implements IMasterServerNode {
	protected Map<String, CommonServerInfo> slaveServers = new HashMap<String, CommonServerInfo>();

	@Override
	public CommonServerInfo getSlaveServer(String serverName) {
		return slaveServers.get(serverName);
	}

}
