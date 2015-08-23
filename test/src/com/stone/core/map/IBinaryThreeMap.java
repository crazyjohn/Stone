package com.stone.core.map;

public interface IBinaryThreeMap<Key, Value> {

	public void put(Key key, Value value);

	public void remove(Key key);

	public Value get(Key key);
}
