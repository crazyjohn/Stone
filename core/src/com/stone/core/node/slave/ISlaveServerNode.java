package com.stone.core.node.slave;

import com.stone.core.node.CommonServerInfo;
import com.stone.core.node.IServerNode;

public interface ISlaveServerNode extends IServerNode {

	public CommonServerInfo getMasterServerInfo(String serverName);

}
