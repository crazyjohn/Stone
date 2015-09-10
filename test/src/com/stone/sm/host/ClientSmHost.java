package com.stone.sm.host;

import com.stone.sm.ClientSmHostContext;

public class ClientSmHost {
	private ClientSmHostContext fsm = new ClientSmHostContext(this);

	public void start() {
		fsm.enterStartState();
		fsm.start();
	}

	public void onEvent() {

	}

	public void log(String msg) {
		System.out.println("ClientSmHost: " + msg);
	}

	private void connect(String host, int port) {
		this.fsm.connect(host, port);
	}

	private void enterScene(String puid, int sceneId) {
		fsm.enterScene(puid, sceneId);
	}

	private void login(String puid) {
		fsm.login(puid);
	}

	private void endBattle() {
		fsm.battleEnd();
	}

	private void battle() {
		fsm.battle();
	}

	public static void main(String[] args) {
		ClientSmHost client = new ClientSmHost();
		// info
		String host = "localhost";
		int port = 9999;
		String puid = "crazyjohn";
		int sceneId = 1;
		// actions
		client.start();
		client.connect(host, port);
		client.login(puid);
		client.enterScene(puid, sceneId);
		client.battle();
		client.endBattle();
	}

}
