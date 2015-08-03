package com.stone.core.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ListVSMap {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		int size = 100000;
		int query = 0;
		Random rand = new Random(size);
		for (int i = 0; i < size; i++) {
			int value = rand.nextInt();
			list.add(value);
			map.put(value, i);
			if (i == size / 2) {
				query = value;
			}
		}
		long listBeginTime = System.currentTimeMillis();
		long listEndTime = 0;
		// query
		for (int i = 0; i < size; i++) {
			if (list.get(i).equals(query)) {
				listEndTime = System.currentTimeMillis();
				break;
			}
		} 
		System.out.println("list query time: " + (listEndTime - listBeginTime));
		long mapBeginTime = System.currentTimeMillis();;
		long mapEndTime = 0;
		int i = map.get(query);
		mapEndTime = System.currentTimeMillis();
		System.out.println("map query time: " + (mapEndTime - mapBeginTime) + " , i: " + i);
	}

}
