package com.i4joy.akka.kok.monster.battle;

import java.util.Random;

public class AttackController
{

	private byte rage_range = 4;
	private byte rage_step = 2;
	private java.util.Random rndVal;

	private static AttackController instance;
	public static AttackController Instance()
	{
		if (instance == null)
		{
			instance = new AttackController();
		}

		return instance;
	}

	public AttackController()
	{
		rndVal = new java.util.Random();
	}

	public final AttackMode GetAttackType(RoleElementData aRoleA, RoleElementData aRoleB)
	{
		short y_range = 30;
		short a_range = 30;
		short z_range = 30;
		short x = 40;

		short y = (short)(aRoleB.missesRate - aRoleA.hitValue);
		short a = (short)(aRoleB.parryValue - aRoleA.piercingValue);
		short z = (short)(aRoleA.critValue - aRoleB.toughnessValue);

		boolean isRage = false;
		if(aRoleA.rageValue<rage_range)
		{
			aRoleA.rageValue+=rage_step;
		}
		else
		{
			aRoleA.rageValue = 0;
			isRage = true;
		}

		if(y < 0)
		{
			y = 0;
		}
		else if(y > y_range)
		{
			y = y_range;
		}

		if(z < 0)
		{
			z = 0;
		}
		else if(z > z_range)
		{
			z = z_range;
		}

		int range = 0;

		if(isRage)
		{
			range = x + y + z;
		}
		else
		{
			if(a < 0)
			{
				a = 0;
			}
			else if(a > a_range)
			{
				a = a_range;
			}

			range = x + y + z + a;
		}


		short rnd = (short)(this.rndVal.nextInt(range+1));

		if(isRage)
		{
			if(rnd<=y)
			{
				return AttackMode.rMisses;
			}
			else if(rnd>y && rnd<=y+z)
			{
				return AttackMode.rCrit;
			}

			return AttackMode.rCommon;
		}
		else
		{
			if(rnd<=y)
			{
				return AttackMode.nMisses;
			}
			else if(rnd>y && rnd<=y+z)
			{
				return AttackMode.nCrit;
			}
			else if(rnd>y+z && rnd<=y+z+a)
			{
				return AttackMode.nParry;
			}

			return AttackMode.nCommon;
		}
	}

	public final boolean IsRage(RoleElementData aRoleA)
	{
		if(aRoleA.rageValue<this.rage_range)
		{
			return false;
		}

		return true;
	}

	public final int GetDamage(RoleElementData aRoleA, RoleElementData aRoleB, AttackMode aMode, RefObject<Boolean> aCritFlag)
	{
		float damagePercent = 0;
		int damage = 0;
		//bool isCrit = false;
		byte fightOrMagic = 0;

		switch(aMode)
		{
		case nCommon:
			{
				damagePercent = BuildDamagePercent(aRoleA.normalSkill.minDamage, aRoleA.normalSkill.maxDamage);
				fightOrMagic = aRoleA.normalSkill.damageType;
			}
			break;
		case nCrit:
			{
				aCritFlag.argvalue = true;
				damagePercent = BuildDamagePercent(aRoleA.normalSkill.minDamage, aRoleA.normalSkill.maxDamage);
				fightOrMagic = aRoleA.normalSkill.damageType;
			}
			break;
		case rCommon:
			{
				damagePercent = BuildDamagePercent(aRoleA.rageSkill.minDamage, aRoleA.rageSkill.maxDamage);
				fightOrMagic = aRoleA.normalSkill.damageType;
			}
			break;
		case rCrit:
			{
				aCritFlag.argvalue = true;
				damagePercent = BuildDamagePercent(aRoleA.rageSkill.minDamage, aRoleA.rageSkill.maxDamage);
				fightOrMagic = aRoleA.normalSkill.damageType;
			}
			break;
		}

		if(fightOrMagic==0)
		{
			damage = (int)((aRoleA.attack - aRoleB.fightDefense) * damagePercent);
		}
		else if(aRoleA.fightOrMagic==1)
		{
			damage = (int)((aRoleA.attack - aRoleB.magicDefense) * damagePercent);
		}

//		if(isCrit)
//		{
//			damage = (int)((float)damage * critPercent);
//		}

		return damage;
	}

	public final int CritDamage(int aDamage, byte aCritPer)
	{
		float critPercent = 1.5f;
		int damage = (int)((float)aDamage * (critPercent*aCritPer/100 + critPercent));
		return damage;
	}

	public final boolean BuffValid(float odds)
	{
		float n = GetFloatRnd(1, 101);

		if(n<=odds)
		{
			return true;
		}

		return false;
	}

	public final int Rnd(int min, int max)
	{
//		return this.rndVal.nextInt(min, max);
		return min+this.rndVal.nextInt(max-min+1);
	}

	public final void setRage_range(byte value)
	{
		rage_range = value;
	}
	public final byte getRage_range()
	{
		return rage_range;
	}
	
	public static void main(String[] args) {
		int a=9;
		int b=18;
		Random r=new Random();
		for(int i=0;i<1000;i++){
			System.out.println(a+r.nextInt(b-a+1));
		}
	}
	
	private float BuildDamagePercent(float aMin, float aMax)
	{
		float fRnd = GetFloatRnd(aMin, aMax);
		return fRnd/100;
	}

	private float GetFloatRnd(float aMin, float aMax)
	{
		int a = (int)(aMin*10000);
		int b = (int)(aMax*10000);
		int iRnd = a+this.rndVal.nextInt(b-a+1);
		float fRnd = (float)iRnd / 10000;
		return fRnd;
	}
}