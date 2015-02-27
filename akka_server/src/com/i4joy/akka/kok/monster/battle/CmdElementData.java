package com.i4joy.akka.kok.monster.battle;

public class CmdElementData
{
	//[攻击者ID][技能ID] [被攻击者ID数组] [技能伤害值数组] [自己挂着的buff数组][自己挂着的buff对hp的影响][当前怒气值][给目标的buff类型数组因为有可能事多个buff][是否有反击数组][反击伤害值数组][连击次数]
	public short id; //command id
	public int attPosID;
	public short skillID;

	/** 
	 是否是怒气攻击
	 
	*/
	public byte isRage; //0:n,1:y

	/** 
	 被攻击者ID 列表
	 
	*/
	public java.util.ArrayList<Integer> defPosID;

	/** 
	 技能伤害值，对应以上。
	 
	*/
	public java.util.ArrayList<Integer> skillDamage;


	/** 
	 战斗前需要清除的BUFF
	 
	*/
	public java.util.ArrayList<Byte> buffRemoveTarget;
	//public List<short[]> buffIdRemove;
	public java.util.ArrayList<Short> buffIdRemove;

	/** 
	 战斗前需要增加的BUFF
	 
	*/
	public java.util.ArrayList<Byte> buffStartTarget;
	//public List<short[]> buffIdStart;
	public java.util.ArrayList<Short> buffIdStart;


	/** 
	 攻击之后需要增加的BUFF
	 
	*/
	public java.util.ArrayList<Byte> buffEndTarget;
	//public List<short[]> buffIdEnd;
	public java.util.ArrayList<Short> buffIdEnd;



	/** 
	 不要了
	 攻击者身上拥有的BUFF  
	 
//	public List<byte> selfBuffID;
//	public List<int> selfBuffRet;
//	public List<int> buffTarget;
//	public List<short> targetBuffID;
	//-------------------------------
	*/

	public byte fightBack; //0:n,1:y
	public int fightBackDamage;

	/** 
	 连击普通攻击次数，
	 
	*/
	public byte comboVal = 0;

	/** 
	 当前怒气值
	 
	*/
	public byte curRage;

	/** 
	 是否是第一条指令
	 1 是第一条指令 2 最后一条指令 0 。。。。
	 
	*/
	public byte roundFlag = 0; //1: round end

	/** 
	 是否是暴击 0:n 1:y
	 
	*/
	public byte isCrit = 0;

	public CmdElementData()
	{
		defPosID = new java.util.ArrayList<Integer>();
		skillDamage = new java.util.ArrayList<Integer>();
		buffRemoveTarget = new java.util.ArrayList<Byte>();
		buffIdRemove = new java.util.ArrayList<Short>();
		buffStartTarget = new java.util.ArrayList<Byte>();
		buffIdStart = new java.util.ArrayList<Short>();
		buffEndTarget = new java.util.ArrayList<Byte>();
		buffIdEnd = new java.util.ArrayList<Short>();

//		selfBuffID = new List<byte>();
//		selfBuffRet = new List<int>();
//		buffTarget = new List<int>();
//		targetBuffID = new List<short>();
	}
}