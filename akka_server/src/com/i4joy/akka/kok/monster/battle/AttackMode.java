package com.i4joy.akka.kok.monster.battle;

public enum AttackMode
{
	nCommon(0), //normal pu tong
	nCrit(1), //normal bao ji
	nMisses(2), //normal wei ming zhong
	nParry(3), //normal ge dang
	rCommon(4), //rage pu tong
	rCrit(5), //rage bao ji
	rMisses(6); //rage wei ming zhong

	private int intValue;
	private static java.util.HashMap<Integer, AttackMode> mappings;
	private synchronized static java.util.HashMap<Integer, AttackMode> getMappings()
	{
		if (mappings == null)
		{
			mappings = new java.util.HashMap<Integer, AttackMode>();
		}
		return mappings;
	}

	private AttackMode(int value)
	{
		intValue = value;
		AttackMode.getMappings().put(value, this);
	}

	public int getValue()
	{
		return intValue;
	}

	public static AttackMode forValue(int value)
	{
		return getMappings().get(value);
	}
}