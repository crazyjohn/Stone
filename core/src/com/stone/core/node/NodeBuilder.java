package com.stone.core.node;

import com.stone.core.node.info.IMasterServerNode;
import com.stone.core.node.info.ISlaveServerNode;
import com.stone.core.node.info.ISuperServerNode;

public class NodeBuilder {

	/**
	 * Build the server node;
	 * 
	 * @return
	 */
	public static ServerNode buildCommonNode() {
		ServerNode node = new ServerNode();
		return node;
	}

	public static IMasterServerNode buildMasterNode() {
		IMasterServerNode node = new MasterServerNode();
		return node;
	}

	public static ISlaveServerNode buildSlaveNode() {
		ISlaveServerNode node = new SlaveServerNode();
		return node;
	}

	public static ISuperServerNode buildSuperNode() {
		ISuperServerNode node = new SuperServerNode();
		return node;
	}
}
