package com.stone.db.agent;

import com.stone.core.msg.IDBCallbackMessage;
import com.stone.db.msg.DBDeleteMessage;
import com.stone.db.msg.DBInsertMessage;
import com.stone.db.msg.DBQueryMessage;
import com.stone.db.msg.DBUpdateMessage;

public interface IDBAgent {

	public IDBCallbackMessage insert(DBInsertMessage msg);

	public IDBCallbackMessage update(DBUpdateMessage msg);

	public IDBCallbackMessage delete(DBDeleteMessage msg);

	public IDBCallbackMessage query(DBQueryMessage msg);
}
