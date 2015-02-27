package com.i4joy.akka.kok.overlord.protocol;

import java.io.Serializable;

public class PlayersGroupStatus implements Serializable, Comparable<PlayersGroupStatus> {
	private String name;
	private int playerNum;
	private long lastUpdate;

	public long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(PlayersGroupStatus o) {
		return playerNum > o.getPlayerNum() ? 1 : -1;
	}
	
//	public static void main(String[] args)
//	{
//		PlayersGroupStatus[] list = new PlayersGroupStatus[10];
//		for(int i = 0; i < 10; i++)
//		{
//			PlayersGroupStatus pgs = new PlayersGroupStatus();
//			pgs.setPlayerNum(i);
//			list[i] = pgs;
//		}
//		Arrays.sort(list);
//		for(int i = 0; i < list.length; i++)
//		{
//			System.out.println(list[i].getPlayerNum());
//		}
//	}

}
