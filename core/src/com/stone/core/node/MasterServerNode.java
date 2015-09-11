package com.stone.core.node;

import java.util.HashMap;
import java.util.Map;

import com.stone.core.node.info.CommonServerInfo;
import com.stone.core.node.info.IMasterServerNode;

public class MasterServerNode extends ServerNode implements IMasterServerNode {
	protected Map<String, CommonServerInfo> slaveServers = new HashMap<String, CommonServerInfo>();

	@Override
	public CommonServerInfo getSlaveServer(String serverName) {
		return slaveServers.get(serverName);
	}

}
