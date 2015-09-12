package com.stone.core.node.info;

import com.stone.core.config.SlaveServerConfig;
import com.stone.core.node.IServerNode;

public interface ISlaveServerNode extends IServerNode{

	public CommonServerInfo getMasterServerInfo(String serverName);
	
	public boolean connectToMasters(SlaveServerConfig config);

}
