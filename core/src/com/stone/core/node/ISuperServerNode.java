package com.stone.core.node;

import com.stone.core.node.master.IMasterServerNode;
import com.stone.core.node.slave.ISlaveServerNode;

public interface ISuperServerNode extends IMasterServerNode, ISlaveServerNode {

}
