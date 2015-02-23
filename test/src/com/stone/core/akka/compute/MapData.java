package com.stone.core.akka.compute;

import java.util.List;

public class MapData {
	private final List<WordCount> dataList;
	
	public List<WordCount> getDataList() {
		return this.dataList;
	}
	
	public MapData(List<WordCount> dataList) {
		this.dataList = dataList;
	}
}
