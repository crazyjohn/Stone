package com.stone.core.config;

import java.util.List;

public interface ISlaveServerConfig {

	public List<MasterAddress> getAllMasterAddresses();

	public void addMasterAddress(String masterName, String host, int port);
}
