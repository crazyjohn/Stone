package com.stone.core.msg;

import com.stone.core.session.BaseSession;

public abstract class BaseSessionMessage<S extends BaseSession> extends BaseMessage implements ISessionMessage<S> {
	protected S session;
	
	
	@Override
	public S getSession() {
		return session;
	}

	@Override
	public void setSession(S session) {
		this.session = session;
	}

}
