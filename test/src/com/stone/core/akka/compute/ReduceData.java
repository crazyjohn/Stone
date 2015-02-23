package com.stone.core.akka.compute;

import java.util.Map;

public class ReduceData {
	private final Map<String, Integer> reduceDataList;
	
	public ReduceData(Map<String, Integer> reduceDataList) {
		this.reduceDataList = reduceDataList;
	}

	public Map<String, Integer> getReduceDataList() {
		return reduceDataList;
	}
}
