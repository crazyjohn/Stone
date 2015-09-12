package com.stone.core.config;

import java.util.ArrayList;
import java.util.List;

import com.stone.proto.Servers.ServerType;

public class SlaveServerConfig extends ServerConfig implements ISlaveServerConfig {
	protected List<MasterAddress> allMasterAddresses = new ArrayList<MasterAddress>();

	@Override
	public List<MasterAddress> getAllMasterAddresses() {
		return allMasterAddresses;
	}

	public void setAllMasterAddresses(List<MasterAddress> allMasterAddresses) {
		this.allMasterAddresses = allMasterAddresses;
	}

	@Override
	public void addMasterAddress(String masterName, String host, int port, ServerType serverType) {
		this.allMasterAddresses.add(new MasterAddress(masterName, host, port, serverType));
	}

}
