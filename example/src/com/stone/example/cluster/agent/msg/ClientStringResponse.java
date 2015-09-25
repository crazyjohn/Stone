package com.stone.example.cluster.agent.msg;

public class ClientStringResponse {
	private final String result;

	public ClientStringResponse(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}
}
