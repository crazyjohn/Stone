package com.stone.core.config.slave;

import java.util.List;

import com.stone.proto.Servers.ServerType;

public interface ISlaveServerConfig {

	public List<MasterAddress> getAllMasterAddresses();

	public void addMasterAddress(String masterName, String host, int port, ServerType serverType);
}
