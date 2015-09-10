package com.stone.sm.host;

import com.stone.sm.ClientSmHostContext;

public class ClientSmHost {
	private ClientSmHostContext fsm = new ClientSmHostContext(this);

	public void start() {
		fsm.enterStartState();
	}
	
	public void onEvent() {
		
	}
}
