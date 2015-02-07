package com.stone.actor.data;

import java.util.List;

public interface ILoadable<DBEntity> {
	public List<DBEntity> loadFormDb();
}
