package com.stone.core.node;

import java.util.HashMap;
import java.util.Map;

import com.stone.core.node.info.CommonServerInfo;
import com.stone.core.node.info.ISlaveServerNode;

public class SlaveServerNode extends ServerNode implements ISlaveServerNode {
	protected Map<String, CommonServerInfo> masterServers = new HashMap<String, CommonServerInfo>();

	@Override
	public CommonServerInfo getMasterServerInfo(String serverName) {
		return masterServers.get(serverName);
	}

}
