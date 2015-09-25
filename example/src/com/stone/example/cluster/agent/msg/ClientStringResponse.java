package com.stone.example.cluster.agent.msg;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClientStringResponse implements Serializable {
	private final String result;

	public ClientStringResponse(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}
}
