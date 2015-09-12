package com.stone.core.config.slave;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stone.core.config.ServerConfig;
import com.stone.proto.Servers.ServerType;

public class SlaveServerConfig extends ServerConfig implements ISlaveServerConfig {
	protected List<MasterAddress> allMasterAddresses = new ArrayList<MasterAddress>();

	@Override
	public List<MasterAddress> getAllMasterAddresses() {
		return Collections.unmodifiableList(allMasterAddresses);
	}

	@Override
	public void addMasterAddress(String masterName, String host, int port, ServerType serverType) {
		this.allMasterAddresses.add(new MasterAddress(masterName, host, port, serverType));
	}

}
