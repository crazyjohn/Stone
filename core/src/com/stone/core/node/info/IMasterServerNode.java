package com.stone.core.node.info;

import com.stone.core.node.IServerNode;

public interface IMasterServerNode extends IServerNode{

	public CommonServerInfo getSlaveServer(String serverName);

}
