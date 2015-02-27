package com.i4joy.akka.kok.monster.item.templet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.i4joy.util.Tools;

public class ItemRewardTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private Map<Integer, rewordBox> mapReword = new HashMap<Integer, rewordBox>();
	// private Map<Integer, probabilityBox> mapProbability = new
	// HashMap<Integer, probabilityBox>();
	private List<probability> list;
	private int total;

	public void addReword(long baseId, int num, int quality, int weight) {
		reword r = new reword();
		r.baseId = baseId;
		r.num = num;
		r.quality = quality;
		r.value = weight;
		if (mapReword.containsKey(r.quality)) {
			mapReword.get(r.quality).addReword(r);
		} else {
			rewordBox rb = new rewordBox();
			rb.quality = r.quality;
			rb.addReword(r);
			mapReword.put(r.quality, rb);
		}
	}

	public void addProbability(int quality, int value) {
		if(list == null)
		{
			list = new ArrayList<probability>();
		}
		probability p = new probability();
		p.quality = quality;
		p.probability = value;
		list.add(p);
		total += value;
	}

	public long[] getReward(int quality) {
		rewordBox rb = mapReword.get(quality);
		reword r = rb.getReword();
		long[] value = new long[2];
		value[0] = r.baseId;
		value[1] = r.num;
		return value;
	}

	public int getQuality() {
		int value = Tools.getRandomNum(0, total);
		for (probability p : list) {
			if (value > p.probability) {
				value -= p.probability;
			} else {
				return p.quality;
			}
		}
		return list.get(0).quality;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<Integer, rewordBox> getMapReword() {
		return mapReword;
	}

	public void setMapReword(Map<Integer, rewordBox> mapReword) {
		this.mapReword = mapReword;
	}
}

class reword {
	long baseId;
	int num;
	int quality;
	int value;
}

class rewordBox {
	List<reword> list;
	int quality;
	int total;

	public void addReword(reword r) {
		if (list == null) {
			list = new ArrayList<reword>();
		}
		list.add(r);
		total += r.value;
	}

	public reword getReword() {
		int value = Tools.getRandomNum(0, total);
		for (reword r : list) {
			if (value > r.value) {
				value -= r.value;
			} else {
				return r;
			}
		}
		return list.get(list.size() - 1);
	}
}

class probability {
	int quality;
	int probability;
}

// class probabilityBox {
// List<probability> list;
// int total;
// int quality;
//
// public void addProbability(probability p) {
// if (list == null) {
// list = new ArrayList<probability>();
// }
// list.add(p);
// total += p.probability;
// }
//
// public int getQuality() {
// int value = Tools.getRandomNum(0, total);
// for (probability p : list) {
// if (value > p.probability) {
// value -= p.probability;
// } else {
// return p.quality;
// }
// }
// return list.get(0).quality;
// }
// }
//

