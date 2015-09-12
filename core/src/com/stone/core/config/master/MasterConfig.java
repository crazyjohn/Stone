package com.stone.core.config.master;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stone.core.config.ServerConfig;
import com.stone.core.config.slave.MasterAddress;

public class MasterConfig extends ServerConfig implements IMasterServerConfig {
	protected List<MasterAddress> allMasterAddresses = new ArrayList<MasterAddress>();

	@Override
	public List<MasterAddress> getAllMasterAddresses() {
		return Collections.unmodifiableList(allMasterAddresses);
	}

	@Override
	public void addMasterPort(String masterName, int port) {
		allMasterAddresses.add(new MasterAddress(masterName, this.getBindIp(), port, this.getServerInfo().getType()));
	}

}
