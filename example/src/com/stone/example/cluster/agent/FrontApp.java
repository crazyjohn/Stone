package com.stone.example.cluster.agent;

public class FrontApp {
	public static void main(String[] args) {
		// start an frontend, just like nginx
		AgentFrontSystem.startup("0");
	}
}
