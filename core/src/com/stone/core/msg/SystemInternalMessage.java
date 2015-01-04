package com.stone.core.msg;

/**
 * 系统内部消息;
 * 
 * @author crazyjohn
 *
 */
public abstract class SystemInternalMessage extends BaseMessage {
	@Override
	protected boolean readBody() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean writeBody() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void execute() throws MessageParseException {
		throw new UnsupportedOperationException();
	}

}
