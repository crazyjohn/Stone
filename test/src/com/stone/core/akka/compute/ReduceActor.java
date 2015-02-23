package com.stone.core.akka.compute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import akka.actor.UntypedActor;

public class ReduceActor extends UntypedActor {

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof MapData) {
			MapData mapData = (MapData) message;
			// logic
			this.getSender().tell(reduce(mapData.getDataList()), this.getSelf());
		} else {
			this.unhandled(message);
		}
	}

	private ReduceData reduce(List<WordCount> dataList) {
		Map<String, Integer> reduceMap = new HashMap<String, Integer>();
		for (WordCount eachWord : dataList) {
			if (reduceMap.containsKey(eachWord.getWord())) {
				Integer value = reduceMap.get(eachWord.getWord());
				value++;
				reduceMap.put(eachWord.getWord(), value);
			} else {
				reduceMap.put(eachWord.getWord(), 1);
			}
		}
		return new ReduceData(reduceMap);
	}

}
