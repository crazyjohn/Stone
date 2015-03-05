package com.stone.core.akka.compute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import akka.actor.UntypedActor;

public class MapActor extends UntypedActor {

	private List<String> STOP_WORDS_LIST = Arrays.asList(new String[]{
			"a", "an", "and"
	});

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			String work = (String) message;
			// logic
			this.getSender().tell(evaluateExpression(work), this.getSelf());
		} else {
			unhandled(message);
		}
	}

	private MapData evaluateExpression(String work) {
		List<WordCount> dataList = new ArrayList<WordCount>();
		StringTokenizer parser = new StringTokenizer(work);
		while (parser.hasMoreTokens()) {
			String word = parser.nextToken().toLowerCase();
			if (!STOP_WORDS_LIST .contains(word)) {
				dataList.add(new WordCount(word, 1));
			}
		}
		return new MapData(dataList);
	}

}
