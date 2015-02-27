package com.i4joy.akka.kok.monster.battle;

public class BuffAddVal
{
	public int damageVal = 0;
	public byte damagePer = 0;
	public byte manDamagePer = 0;
	public byte faction1DamagePer = 0;
	public byte faction2DamagePer = 0;
	public byte faction3DamagePer = 0;
	public byte fightDamagePer = 0;
	public byte magicDamagePer = 0;
	public byte addHpFromAttPer = 0;
	public byte targetSubRage = 0;
	public byte comboVal = 0;
	public byte critMulPer = 0;
	public int damageFromHp = 0;
	public int hpAddPer = 0;
	public int fightDamageVal = 0;
	public int magicDamageVal = 0;

	public byte damageSubPer = 0;
	public byte manDamageSubPer = 0;
	public byte faction1DamageSubPer = 0;
	public byte faction2DamageSubPer = 0;
	public byte faction3DamageSubPer = 0;
	public byte fightDamageSubPer = 0;
	public byte magicDamageSubPer = 0;
	public int fightDamageSubVal = 0;
	public int magicDamageSubVal = 0;
	public int damageSubVal = 0;

	public final void AttDamageHandle(RefObject<Integer> aDamage, RefObject<Integer> aHp, byte fightOrMagic, byte sex, byte targetSex, byte faction, byte targetFaction, int targetMaxHp)
	{
		if(damageVal>0)
		{
			aDamage.argvalue += damageVal;
		}

		if(damagePer>0)
		{
			aDamage.argvalue += (aDamage.argvalue * damagePer / 100);
		}

		if(fightDamagePer>0)
		{
			if(fightOrMagic==0)
			{
				aDamage.argvalue += (aDamage.argvalue * fightDamagePer / 100);
			}
		}

		if(magicDamagePer>0)
		{
			if(fightOrMagic==1)
			{
				aDamage.argvalue += (aDamage.argvalue * magicDamagePer / 100);
			}
		}

		if(addHpFromAttPer>0)
		{
			aHp.argvalue += (aDamage.argvalue * addHpFromAttPer / 100);
		}

		if(damageFromHp>0)
		{
			aDamage.argvalue += (targetMaxHp * damageFromHp / 100);
		}

		if(fightDamageVal>0)
		{
			if(fightOrMagic==0)
			{
				aDamage.argvalue += fightDamageVal;
			}
		}

		if(magicDamageVal>0)
		{
			if(fightOrMagic==1)
			{
				aDamage.argvalue += magicDamageVal;
			}
		}

		if(manDamagePer>0 && targetSex==1)
		{
			aDamage.argvalue += (aDamage.argvalue * manDamagePer / 100);
		}

		if(faction1DamagePer>0 && targetFaction==1)
		{
			aDamage.argvalue += (aDamage.argvalue * faction1DamagePer / 100);
		}

		if(faction2DamagePer>0 && targetFaction==2)
		{
			aDamage.argvalue += (aDamage.argvalue * faction2DamagePer / 100);
		}

		if(faction3DamagePer>0 && targetFaction==3)
		{
			aDamage.argvalue += (aDamage.argvalue * faction3DamagePer / 100);
		}
	}

	public final void DefDamageHandle(RefObject<Integer> aDamage, byte fightOrMagic, byte srcSex, byte srcFaction)
	{
		if(damageSubPer>0)
		{
			aDamage.argvalue -= (aDamage.argvalue * damageSubPer / 100);
		}

		if(fightDamageSubPer>0)
		{
			if(fightOrMagic==0)
			{
				aDamage.argvalue -= (aDamage.argvalue * fightDamageSubPer / 100);
			}
		}

		if(magicDamageSubPer>0)
		{
			if(fightOrMagic==1)
			{
				aDamage.argvalue -= (aDamage.argvalue * magicDamageSubPer / 100);
			}
		}

		if(fightDamageSubVal>0)
		{
			if(fightOrMagic==0)
			{
				aDamage.argvalue -= fightDamageSubVal;
			}
		}

		if(magicDamageSubVal>0)
		{
			if(fightOrMagic==1)
			{
				aDamage.argvalue -= magicDamageSubVal;
			}
		}

		if(damageSubVal>0)
		{
			aDamage.argvalue -= damageSubVal;
		}

		if(manDamageSubPer>0 && srcSex==1)
		{
			aDamage.argvalue -=(aDamage.argvalue * manDamageSubPer / 100);
		}

		if(faction1DamageSubPer>0 && srcFaction==1)
		{
			aDamage.argvalue -=(aDamage.argvalue * faction1DamageSubPer / 100);
		}

		if(faction2DamageSubPer>0 && srcFaction==2)
		{
			aDamage.argvalue -=(aDamage.argvalue * faction2DamageSubPer / 100);
		}

		if(faction3DamageSubPer>0 && srcFaction==3)
		{
			aDamage.argvalue -=(aDamage.argvalue * faction3DamageSubPer / 100);
		}

	}

	public final void Clear()
	{
		damageVal = 0;
		damagePer = 0;
		manDamagePer = 0;
		faction1DamagePer = 0;
		faction2DamagePer = 0;
		faction3DamagePer = 0;
		fightDamagePer = 0;
		magicDamagePer = 0;
		addHpFromAttPer = 0;
		targetSubRage = 0;
		comboVal = 0;
		critMulPer = 0;
		damageFromHp = 0;
		hpAddPer = 0;
		fightDamageVal = 0;
		magicDamageVal = 0;

		damageSubPer = 0;
		manDamageSubPer = 0;
		faction1DamageSubPer = 0;
		faction2DamageSubPer = 0;
		faction3DamageSubPer = 0;
		fightDamageSubPer = 0;
		magicDamageSubPer = 0;
		fightDamageSubVal = 0;
		magicDamageSubVal = 0;
		damageSubVal = 0;
	}

	public final BuffAddVal Copy()
	{
		BuffAddVal tmp = new BuffAddVal();

		tmp.damageVal = damageVal;
		tmp.damagePer = damagePer;
		tmp.manDamagePer = manDamagePer;
		tmp.faction1DamagePer = faction1DamagePer;
		tmp.faction2DamagePer = faction2DamagePer;
		tmp.faction3DamagePer = faction3DamagePer;
		tmp.fightDamagePer = fightDamagePer;
		tmp.magicDamagePer = magicDamagePer;
		tmp.addHpFromAttPer = addHpFromAttPer;
		tmp.targetSubRage = targetSubRage;
		tmp.comboVal = comboVal;
		tmp.critMulPer = critMulPer;
		tmp.damageFromHp = damageFromHp;
		tmp.hpAddPer = hpAddPer;
		tmp.fightDamageVal = fightDamageVal;
		tmp.magicDamageVal = magicDamageVal;

		tmp.damageSubPer = damageSubPer;
		tmp.manDamageSubPer = manDamageSubPer;
		tmp.faction1DamageSubPer = faction1DamageSubPer;
		tmp.faction2DamageSubPer = faction2DamageSubPer;
		tmp.faction3DamageSubPer = faction3DamageSubPer;
		tmp.fightDamageSubPer = fightDamageSubPer;
		tmp.magicDamageSubPer = magicDamageSubPer;
		tmp.fightDamageSubVal = fightDamageSubVal;
		tmp.magicDamageSubVal = magicDamageSubVal;
		tmp.damageSubVal = damageSubVal;

		return tmp;
	}

}