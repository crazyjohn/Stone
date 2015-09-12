package com.stone.core.node.master;

import com.stone.core.node.CommonServerInfo;
import com.stone.core.node.IServerNode;

public interface IMasterServerNode extends IServerNode{

	public CommonServerInfo getSlaveServer(String serverName);

}
