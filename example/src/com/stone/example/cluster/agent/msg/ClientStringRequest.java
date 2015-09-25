package com.stone.example.cluster.agent.msg;

public class ClientStringRequest {
	private final String info;

	public ClientStringRequest(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
