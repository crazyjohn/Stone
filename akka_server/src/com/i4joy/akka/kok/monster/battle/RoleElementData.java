package com.i4joy.akka.kok.monster.battle;

public class RoleElementData
{

	/** 
	 战斗位置ID
	 
	*/
	public int posID;

	/** 
	 The max hp.需要赋值
	*/
	public int maxHp;
	
	/**
	 * 当前生命，需要赋值
	 */
	public int hp;

	/** 
	 The sex.ID 需要赋值
	*/
	public byte sex; //0:man, 1:woman

	/**
	 * 阵营，需要赋值
	 */
	public byte faction; //zhen ying
	
	public byte rageValue;

	/** 
	 The current buff list.
	 战斗过程中拥有的BUFF
	*/
	public java.util.ArrayList<BuffElementData> curBuffList;

	/** 
	 The attack.
	 攻击力 需要赋值
	*/
	public int attack; //gong ji
	
	/** 
	 The attack init.
	 攻击基础值 需要赋值，同攻击力
	*/
	public int attackInit;

	/** 
	 The fight defense.
	 外防御 需要赋值
	*/
	public int fightDefense; //wai fang
	
	/**
	 * 外防基础值 需要赋值，同外防
	 */
	public int fightDefenseInit; //wai fang
	
	/** 
	 外攻免伤比例
	*/
	public float fightDefProportion; //wai gong mian shang bi li


	/** 
	 法防 需要赋值
	*/
	public int magicDefense; //nei fang
	
	/**
	 * 需要赋值，同法防
	 */
	public int magicDefenseInit; //nei fang



	public float magicDefProportion; //nei gong mian shang bi li

	/**
	 * 需要赋值
	 */
	public short critValue; //bao ji zhi
	
	/**
	 * 需要赋值
	 */
	public short toughnessValue; //ren xing zhi
	
	/**
	 * 需要赋值
	 */
	public short hitValue; //ming zhong zhi
	
	/**
	 * 需要赋值
	 */
	public short missesRate; //shan bi zhi
	
	/**
	 * 需要赋值
	 */
	public short parryValue; //ge dang
	
	/**
	 * 需要赋值
	 */
	public short piercingValue; //chuan ci
	
	public byte fightOrMagic; //shang hai lei xing, 0:fight 1:magic

	/**
	 * 需要赋值
	 */
	public SkillElementData normalSkill;
	
	/**
	 * 需要赋值
	 */
	public SkillElementData rageSkill;
	
	/**
	 * 天赋，需要赋值
	 */
	public java.util.ArrayList<SkillElementData> passiveSkill;


	public java.util.ArrayList<Short> attack1HitFrame;
	public java.util.ArrayList<Short> attack2HitFrame;

/**-------------------------
*/
	public BuffAddVal addVal;
	public short attackNum = 0;
	public boolean isStun = false; //shi fou xuan yun
	public boolean isCrit = false; //yi ding bao ji
	public boolean isDamageToHp = false;
	public AvatarElement avatar;

	public RoleElementData()
	{
		curBuffList = new java.util.ArrayList<BuffElementData>();
		passiveSkill = new java.util.ArrayList<SkillElementData>();
		addVal = new BuffAddVal();
		normalSkill = new SkillElementData(null);
		rageSkill = new SkillElementData(null);
		avatar = new AvatarElement();
		attack1HitFrame = new java.util.ArrayList<Short>();
		attack2HitFrame = new java.util.ArrayList<Short>();
	}

	public final RoleElementData Copy()
	{
		RoleElementData rd = new RoleElementData();

		rd.posID = this.posID;
		rd.maxHp = this.maxHp;
		rd.hp = this.hp;
		rd.sex = this.sex;
		rd.faction = this.faction;
		rd.rageValue = this.rageValue;

		for(BuffElementData de : curBuffList)
		{
			BuffElementData tmp = new BuffElementData(de);
			rd.curBuffList.add(tmp);
		}

		rd.attack = this.attack;
		rd.attackInit = this.attackInit;
		rd.fightDefense = this.fightDefense;
		rd.fightDefenseInit = this.fightDefenseInit;
		rd.fightDefProportion = this.fightDefProportion;
		rd.magicDefense = this.magicDefense;
		rd.magicDefenseInit = this.magicDefenseInit;
		rd.magicDefProportion = this.magicDefProportion;
		rd.critValue = this.critValue;
		rd.toughnessValue = this.toughnessValue;
		rd.hitValue = this.hitValue;
		rd.missesRate = this.missesRate;
		rd.parryValue = this.parryValue;
		rd.piercingValue = this.piercingValue;
		rd.fightOrMagic = this.fightOrMagic;
		rd.normalSkill = new SkillElementData(normalSkill);
		rd.rageSkill = new SkillElementData(rageSkill);

		for(SkillElementData de : passiveSkill)
		{
			SkillElementData tmp = new SkillElementData(de);
			rd.passiveSkill.add(tmp);
		}

		for(short de : attack1HitFrame)
		{
			short tmp = de;
			rd.attack1HitFrame.add(tmp);
		}

		for(short de : attack2HitFrame)
		{
			short tmp = de;
			rd.attack2HitFrame.add(tmp);
		}

		rd.addVal = this.addVal.Copy();
		rd.attackNum = this.attackNum;
		rd.isStun = this.isStun;
		rd.isCrit = this.isCrit;
		rd.isDamageToHp = this.isDamageToHp;
		rd.avatar = this.avatar.Copy();

		return rd;
	}

}