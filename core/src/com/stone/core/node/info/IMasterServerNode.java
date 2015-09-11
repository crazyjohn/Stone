package com.stone.core.node.info;

import com.stone.core.node.IStoneNode;

public interface IMasterServerNode extends IStoneNode{

	public CommonServerInfo getSlaveServer(String serverName);

}
