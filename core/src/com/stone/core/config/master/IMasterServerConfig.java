package com.stone.core.config.master;

import java.util.List;

import com.stone.core.config.slave.MasterAddress;

public interface IMasterServerConfig {

	public List<MasterAddress> getAllMasterAddresses();

	public void addMasterPort(String masterName, int port);
}
