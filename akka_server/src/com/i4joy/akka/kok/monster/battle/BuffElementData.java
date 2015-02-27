package com.i4joy.akka.kok.monster.battle;

public class BuffElementData
{
	public short bufferID;
	public byte bufferTrigger;
	public byte bufferOdds;
	public byte bufferRound;
	public byte bufferMaxRound;
	public float bufferResult;
	public byte bufferIsOne; //one round, 1:n,0:y
	public byte bufferTargetType; //1:自己, 2:己方全体, 3:己方当前生命值最小 4：己方全体随机 5：己方除自己外随机 6：敌方全体 7：敌方全体随机 8：被攻击者
	public byte bufferRandCount;
	public short bufferEffectID;
	
	public BuffElementData(){
		
	}

	public BuffElementData(BuffElementData data)
	{
		if(data!=null)
		{
			bufferID = data.bufferID;
			bufferOdds = data.bufferOdds;
			bufferRound = data.bufferRound;
			bufferMaxRound = data.bufferMaxRound;
			bufferResult = data.bufferResult;
			bufferIsOne = data.bufferIsOne;
			bufferTargetType = data.bufferTargetType;
			bufferRandCount = data.bufferRandCount;
			bufferEffectID = data.bufferEffectID;
			bufferTrigger = data.bufferTrigger;
		}
	}
}