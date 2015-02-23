package com.stone.core.akka.compute;

import java.util.HashMap;
import java.util.Map;

import akka.actor.UntypedActor;

public class AggregateActor extends UntypedActor {
	private Map<String, Integer> finalReduceMap = new HashMap<String, Integer>();

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof ReduceData) {
			ReduceData reduceData = (ReduceData) message;
			aggretateInMemoryReduce(reduceData);
		} else if (message instanceof Result) {
			this.getSender().tell(finalReduceMap.toString(), this.getSelf());
		} else {
			this.unhandled(message);
		}
	}

	private void aggretateInMemoryReduce(ReduceData reduceData) {
		Integer count = null;
		for (String key : reduceData.getReduceDataList().keySet()) {
			if (this.finalReduceMap.containsKey(key)) {
				count = reduceData.getReduceDataList().get(key) + this.finalReduceMap.get(key);
				this.finalReduceMap.put(key, count);
			} else {
				this.finalReduceMap.put(key, reduceData.getReduceDataList().get(key));
			}
		}
	}
}
