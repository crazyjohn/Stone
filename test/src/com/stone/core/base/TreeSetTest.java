package com.stone.core.base;

import java.util.TreeSet;

/**
 * TreeSet test;
 * <p>
 * output:<br>
 * <code>
 * [49, 59, 70, 80, 89, 99]<br>
 * [49, 59, 70, 888, 89, 99, 888]
 * </code>
 * 
 * @author crazyjohn
 *
 */
public class TreeSetTest {

	public static void main(String[] args) {
		TreeSet<HumanScore> set = new TreeSet<HumanScore>();
		// add human
		HumanScore score80 = new HumanScore(80);
		set.add(score80);
		set.add(new HumanScore(59));
		set.add(new HumanScore(49));
		set.add(new HumanScore(89));
		set.add(new HumanScore(99));
		set.add(new HumanScore(70));
		System.out.println(set);
		// add the same
		score80.setScore(888);
		set.add(score80);
		System.out.println(set);
	}

	static class HumanScore implements Comparable<HumanScore> {
		private int score;

		public HumanScore(int score) {
			this.score = score;
		}

		public void setScore(int score) {
			this.score = score;
		}

		@Override
		public int compareTo(HumanScore o) {
			return this.score - o.score;
		}

		@Override
		public String toString() {
			return String.valueOf(score);
		}

	}

}
