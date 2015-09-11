package com.stone.core.node.info;

import com.stone.core.node.IStoneNode;

public interface ISlaveServerNode extends IStoneNode{

	public CommonServerInfo getMasterServerInfo(String serverName);

}
