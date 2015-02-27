package com.i4joy.akka.kok.monster.battle;

import java.util.ArrayList;

public class SkillElementData
{
	public short id;
//	public byte  trigger;
	public byte targetType;
	public byte targetCount;
	public short minDamage;
	public short maxDamage;
	public java.util.ArrayList<BuffElementData> buff=new ArrayList<BuffElementData>();
	public byte damageType;
	
	public SkillElementData(){
		
	}

	public SkillElementData(SkillElementData data)
	{
		buff = new java.util.ArrayList<BuffElementData>();

		if(data!=null)
		{
			id = data.id;
//			trigger = data.trigger;
			targetType = data.targetType;
			targetCount = data.targetCount;
			minDamage = data.minDamage;
			maxDamage = data.maxDamage;
			damageType = data.damageType;

			buff = new java.util.ArrayList<BuffElementData>();
			for(BuffElementData de : data.buff)
			{
				BuffElementData tmp = new BuffElementData(de);
				buff.add(tmp);
			}
		}
	}
}