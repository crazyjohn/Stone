package com.stone.example.cluster.agent.msg;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClientStringRequest implements Serializable{
	private final String info;

	public ClientStringRequest(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}
}
