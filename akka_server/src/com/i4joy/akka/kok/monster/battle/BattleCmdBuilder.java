package com.i4joy.akka.kok.monster.battle;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class BattleCmdBuilder
{

	private static int[][] teamOrder = {{1,2,3,4,5},{2,1,3,4,5},{3,2,4,1,5},{4,3,5,2,1},{5,4,3,2,1}};
	private static int[][] enemyOrder = {{6,7,8,9,10},{7,6,8,9,10},{8,7,9,6,10},{9,8,10,7,6},{10,9,8,7,6}};
	private static byte[] tmpOrder1 = {1,2,3,4,5};
	private static byte[] tmpOrder2 = {6,7,8,9,10};
	private static int maxRound = 3;
	private static int[] attOrder = {1,7,3,9,5,6,2,8,4,10};


	public static void BuildCmd(java.util.Hashtable<String,RoleElementData> aRoleTab, List<CmdElementData> aCmdList)
	{
		java.util.ArrayList<RoleElementData> rdTarget = new java.util.ArrayList<RoleElementData>();
		java.util.ArrayList<RoleElementData> teamReserve = new java.util.ArrayList<RoleElementData>();
		java.util.ArrayList<RoleElementData> enemyReserve = new java.util.ArrayList<RoleElementData>();

		for(int i=11;i<14;i++)
		{
			if(aRoleTab.get((new Integer(i)).toString())!=null)
			{
				RoleElementData tmpRdS = (RoleElementData)aRoleTab.get((new Integer(i)).toString());
				RoleElementData tmpRdT = tmpRdS.Copy();
				teamReserve.add(tmpRdT);
				aRoleTab.remove((new Integer(i)).toString());
			}
		}

		for(int i=14;i<17;i++)
		{
			if(aRoleTab.get((new Integer(i)).toString())!=null)
			{
				RoleElementData tmpRdS = (RoleElementData)aRoleTab.get((new Integer(i)).toString());
				RoleElementData tmpRdT = tmpRdS.Copy();
				enemyReserve.add(tmpRdT);
				aRoleTab.remove((new Integer(i)).toString());
			}
		}

		//检查角色是否有全局buff，如果有根据buff效果修改角色相应数值
		BattleCmdBuilder.CheckOverallBuff(aRoleTab);

		int cmdStartIdx = 0;
		short cmdCurIdx = 0;

		for(int r=0;r<maxRound;r++)
		{
			//敌我人剩余人员数量
			byte teamNum = 0;
			byte enemyNum = 0;

			//敌我剩余hp
			int teamAllHp = 0;
			int enemyAllHp = 0;

			//按照攻击顺序挨个生成指令
			for(byte i=0;i<attOrder.length;i++)
			{
				int n = attOrder[i];

				//当前激活的角色
				String key = (new Integer(n)).toString();
				RoleElementData rd = (RoleElementData)aRoleTab.get(key);

				if(rd.hp>0 && rd.isStun==false)
				{
					rd.attackNum++;

					CmdElementData ced = new CmdElementData();

					//检查临时buff
					RefObject<CmdElementData> tempRef_ced = new RefObject<CmdElementData>(ced);
					CheckTempBuff(rd, tempRef_ced);
					ced = tempRef_ced.argvalue;

					//检查被动技能1,2
					CheckPassiveSkill(rd, aRoleTab, new byte[]{1, 2}, ced);

					//首次攻击
					if(rd.attackNum==1)
					{
						//检查被动技能7
						CheckPassiveSkill(rd, aRoleTab, new byte[]{7}, ced);
					}


					ced.attPosID = rd.posID;

					//清空目标列表待用
					rdTarget.clear();

					//根据技能范围添加目标到目标列表
					byte targetType = 0;
					byte targetCount = 0;
					if(AttackController.Instance().IsRage(rd))
					{
						ced.skillID = rd.rageSkill.id;
						ced.isRage = 1;
						targetType = rd.rageSkill.targetType;
						targetCount = rd.rageSkill.targetCount;
						//检查被动技能14
						CheckPassiveSkill(rd, aRoleTab, new byte[]{14}, ced);
					}
					else
					{
						ced.skillID = rd.normalSkill.id;
						ced.isRage = 0;
						targetType = rd.normalSkill.targetType;
						targetCount = rd.normalSkill.targetCount;
						//检查被动技能13
						CheckPassiveSkill(rd, aRoleTab, new byte[]{13}, ced);
					}

					RefObject<java.util.ArrayList<RoleElementData>> tempRef_rdTarget = new RefObject<java.util.ArrayList<RoleElementData>>(rdTarget);
					BattleCmdBuilder.ScanTarget(targetType, targetCount, rd, aRoleTab, tempRef_rdTarget);
					rdTarget = tempRef_rdTarget.argvalue;

					//遍历目标列表，对每个目标发动攻击
					for(byte j=0;j<rdTarget.size();j++)
					{
						ced.defPosID.add(rdTarget.get(j).posID);

						AttackMode am = AttackController.Instance().GetAttackType(rd, rdTarget.get(j));

						if(am==AttackMode.nParry && rdTarget.get(j).isStun==false) //这里应该判断对方是否是眩晕状态
						{
							//攻击被目标格挡了，在这里处理对方反击
							boolean crit = false;
							RefObject<Boolean> tempRef_crit = new RefObject<Boolean>(crit);
							int damage = AttackController.Instance().GetDamage(rdTarget.get(j), rd, AttackMode.nCommon, tempRef_crit);
							crit = tempRef_crit.argvalue;
							ced.skillDamage.add(0);
							ced.fightBack = 1;
							ced.fightBackDamage = damage;
							rd.hp-=damage;
						}
						else
						{
							//给目标添加伤害
							ced.isCrit = 0;
							boolean crit = false;
							RefObject<Boolean> tempRef_crit2 = new RefObject<Boolean>(crit);
							int damage = AttackController.Instance().GetDamage(rd, rdTarget.get(j), am, tempRef_crit2);
							crit = tempRef_crit2.argvalue;
							if((crit || rd.isCrit) && rd.addVal.comboVal<=0)
							{
								ced.isCrit = 1;
								damage = AttackController.Instance().CritDamage(damage, rd.addVal.critMulPer);
							}

							RefObject<Integer> tempRef_damage = new RefObject<Integer>(damage);
							RefObject<Integer> tempRef_hp = new RefObject<Integer>(rd.hp);
							rd.addVal.AttDamageHandle(tempRef_damage, tempRef_hp, rd.fightOrMagic, rd.sex, rdTarget.get(j).sex, rd.faction, rdTarget.get(j).faction, rdTarget.get(j).maxHp);
							damage = tempRef_damage.argvalue;
							rd.hp = tempRef_hp.argvalue;
							if(rd.hp>rd.maxHp)
							{
								rd.hp = rd.maxHp;
							}

							RefObject<Integer> tempRef_damage2 = new RefObject<Integer>(damage);
							rdTarget.get(j).addVal.DefDamageHandle(tempRef_damage2, rd.fightOrMagic, rd.sex, rd.faction);
							damage = tempRef_damage2.argvalue;
							if(rd.isDamageToHp)
							{
								rdTarget.get(j).hp+=damage;
								if(rdTarget.get(j).hp>rdTarget.get(j).maxHp)
								{
									rdTarget.get(j).hp = rdTarget.get(j).maxHp;
								}
							}
							else
							{
								rdTarget.get(j).hp-=damage;
							}

							ced.skillDamage.add(damage);
							ced.fightBack = 0;
							ced.fightBackDamage = 0;
							ced.comboVal = rd.addVal.comboVal;

						}

						if(rdTarget.get(j).hp<=0)
						{
							//检查被动技能9
							CheckPassiveSkill(rd, aRoleTab, new byte[]{9}, ced);
							//检查被动技能10
							CheckPassiveSkill(rdTarget.get(j), aRoleTab, new byte[]{10}, ced);

							//CheckRoleIsDie(rdTarget[j], aRoleTab, teamReserve, enemyReserve, ref teamNum, ref enemyNum);
						}
						else
						{
							//检查被动技能3,4,5,6,15
							CheckPassiveSkill(rdTarget.get(j), aRoleTab, new byte[]{3, 4, 5, 6, 15}, ced);

							if(rd.sex == 1)
							{
								//检查被动技能11
								CheckPassiveSkill(rdTarget.get(j), aRoleTab, new byte[]{11}, ced);
							}
							else if(rd.sex == 2)
							{
								//检查被动技能12
								CheckPassiveSkill(rdTarget.get(j), aRoleTab, new byte[]{12}, ced);
							}
						}

						if(ced.isRage==1)
						{
							if(rd.rageSkill.buff!=null)
							{
								java.util.ArrayList<Short> buffIdList = new java.util.ArrayList<Short>();
								for(int m=0;m<rd.rageSkill.buff.size();m++)
								{
									if(AttackController.Instance().BuffValid(rd.rageSkill.buff.get(m).bufferOdds))
									{
										//通过buff成功概率算出已经成功，给目标添加buff
										java.util.ArrayList<RoleElementData> buffTargetList = new java.util.ArrayList<RoleElementData>();
										BuffElementData tbf = rd.rageSkill.buff.get(m);
										RefObject<java.util.ArrayList<RoleElementData>> tempRef_buffTargetList = new RefObject<java.util.ArrayList<RoleElementData>>(buffTargetList);
										BattleCmdBuilder.ScanTarget(tbf.bufferTargetType, tbf.bufferRandCount, rd, aRoleTab, tempRef_buffTargetList);
										buffTargetList = tempRef_buffTargetList.argvalue;
										for(RoleElementData t : buffTargetList)
										{
											t.curBuffList.add(tbf);
											//ced.buffTarget.Add(t.posID);
											//ced.targetBuffID.Add(tbf.bufferID);
											ced.buffIdEnd.add(tbf.bufferEffectID);
											ced.buffEndTarget.add((byte)t.posID);
										}
									}
								}
							}
						}
						else
						{
							if(rd.normalSkill.buff!=null)
							{
								for(int m=0;m<rd.normalSkill.buff.size();m++)
								{
									if(AttackController.Instance().BuffValid(rd.normalSkill.buff.get(m).bufferOdds))
									{
										//通过buff成功概率算出已经成功，给目标添加buff
										java.util.ArrayList<RoleElementData> buffTargetList = new java.util.ArrayList<RoleElementData>();
										BuffElementData tbf = rd.normalSkill.buff.get(m);
										RefObject<java.util.ArrayList<RoleElementData>> tempRef_buffTargetList2 = new RefObject<java.util.ArrayList<RoleElementData>>(buffTargetList);
										BattleCmdBuilder.ScanTarget(tbf.bufferTargetType, tbf.bufferRandCount, rd, aRoleTab, tempRef_buffTargetList2);
										buffTargetList = tempRef_buffTargetList2.argvalue;
										for(RoleElementData t : buffTargetList)
										{
											t.curBuffList.add(tbf);
											//ced.buffTarget.Add(t.posID);
											//ced.targetBuffID.Add(tbf.bufferID);
											ced.buffIdEnd.add(tbf.bufferEffectID);
											ced.buffEndTarget.add((byte)t.posID);
										}
									}
								}
							}
						}

					}

					ced.roundFlag = 0;

					//挂着的buff追加到指令,在实施攻击前释放
//					List<short> tmpStartBuffID = new List<short>();
//					for(int j=0;j<rd.curBuffList.Count;j++)
//					{
//						tmpStartBuffID.Add(rd.curBuffList[j].bufferID);
//					}
//					ced.buffIdStart.Add(tmpStartBuffID.ToArray());
//					ced.buffStartTarget.Add((byte)rd.posID);

					ced.curRage = rd.rageValue;

					if(rd.posID<BattleCmdBuilder.tmpOrder2[0])
					{
						if(rd.hp>0)
						{
							teamNum++;
							teamAllHp+=rd.hp;
						}
					}
					else
					{
						if(rd.hp>0)
						{
							enemyNum++;
							enemyAllHp+=rd.hp;
						}

					}

					if(ced.defPosID.size()>0)
					{
						ced.id = (short)(cmdCurIdx+1);
						aCmdList.add(ced);
						cmdCurIdx++;

						CmdLog(ced);
					}
				}

//				CheckRoleIsDie(rd, aRoleTab, teamReserve, enemyReserve, ref teamNum, ref enemyNum);

			}

			if(aCmdList.size()>0)
			{
				if(cmdStartIdx>=aCmdList.size())
				{
					cmdStartIdx = aCmdList.size()-1;
				}

				CmdElementData tmpCmd1 = aCmdList.get(cmdStartIdx);
				CmdElementData tmpCmd2 = aCmdList.get(aCmdList.size()-1);

				//代表一回合开始的标记
				tmpCmd1.roundFlag = 1;

				//代表一回合结束的标记
				tmpCmd2.roundFlag = 2;

				HpLog(aRoleTab);

				RefObject<Byte> tempRef_teamNum = new RefObject<Byte>(teamNum);
				RefObject<Byte> tempRef_enemyNum = new RefObject<Byte>(enemyNum);
				CheckAllRoleIsDie(aRoleTab, teamReserve, enemyReserve, tempRef_teamNum, tempRef_enemyNum);
				teamNum = tempRef_teamNum.argvalue;
				enemyNum = tempRef_enemyNum.argvalue;

				if(teamNum==0 || enemyNum==0)
				{
					//有一方人员全部阵亡后，跳出循环
					//MDebug.Log("=============================================");
					break;
				}

				cmdStartIdx = aCmdList.size();

			}
		}
	}

	public static void HpLog(java.util.Hashtable aRoleTab)
	{
//		string str = "";
//		foreach(DictionaryEntry entity in aRoleTab)
//		{
//			RoleElementData rd = (RoleElementData)entity.Value;
//			str+=rd.hp.ToString();
//			str+=",";
//		}
//		
//		str.Remove(str.Length-1);
//		str+="; ";
//		MDebug.Log(str);
	}


	public static void CmdLog(CmdElementData aCmd)
	{
		return;

//		PublicDataManager.Instance.cmdAddIndex++;
//		MDebug.Log("-----------------command start "+PublicDataManager.Instance.cmdAddIndex+"-----------------");
//		MDebug.Log("attPosID:"+aCmd.attPosID);
//		MDebug.Log("skillID:"+aCmd.skillID);
//
//		foreach(int de in aCmd.defPosID)
//		{
//			MDebug.Log("defPosID:"+de);
//		}
//		
//		MDebug.Log("isRage:"+aCmd.isRage);
//		foreach(int de in aCmd.skillDamage)
//		{
//			MDebug.Log("skillDamage:"+de);
//		}
//		
//		foreach(byte de in aCmd.buffRemoveTarget)
//		{
//			MDebug.Log("buffRemoveTarget:"+de);
//		}
//		
//		foreach(short de in aCmd.buffIdRemove)
//		{
//			MDebug.Log("buffIdRemove:"+de);
//		}
//		
//		foreach(byte de in aCmd.buffStartTarget)
//		{
//			MDebug.Log("buffStartTarget:"+de);
//		}
//		
//		foreach(short de in aCmd.buffIdStart)
//		{
//			MDebug.Log("buffIdStart:"+de);
//		}
//		
//		foreach(byte de in aCmd.buffEndTarget)
//		{
//			MDebug.Log("buffEndTarget:"+de);
//		}
//		
//		foreach(short de in aCmd.buffIdEnd)
//		{
//			MDebug.Log("buffIdEnd:"+de);
//		}
//		
//		MDebug.Log("fightBack:"+aCmd.fightBack);//0:n,1:y
//		MDebug.Log("fightBackDamage:"+aCmd.fightBackDamage);
//		MDebug.Log("comboVal:"+aCmd.comboVal);
//		MDebug.Log("curRage:"+aCmd.curRage);
//		MDebug.Log("roundFlag:"+aCmd.roundFlag);
//		MDebug.Log("-----------------command end-----------------");
	}

	public static void CheckRoleIsDie(RoleElementData aRd, java.util.Hashtable aRoleTab, java.util.ArrayList<RoleElementData> aTeamReserve, java.util.ArrayList<RoleElementData> aEnemyReserve, RefObject<Byte> aTeamNum, RefObject<Byte> aEnemyNum)
	{
		if(aRd.hp<=0)
		{
			if(aRd.posID<BattleCmdBuilder.tmpOrder2[0])
			{ //RoleElementData
				if(aTeamReserve.size()>0)
				{
					aTeamReserve.get(0).posID = aRd.posID;
					aRoleTab.remove(aRd.posID);
					aRoleTab.put(aTeamReserve.get(0).posID, aTeamReserve.get(0));
					aTeamReserve.remove(0);
					aTeamNum.argvalue++;
				}
			}
			else
			{
				if(aEnemyReserve.size()>0)
				{
					aEnemyReserve.get(0).posID = aRd.posID;
					aRoleTab.remove(aRd.posID);
					aRoleTab.put(aEnemyReserve.get(0).posID, aEnemyReserve.get(0));
					aEnemyReserve.remove(0);
					aEnemyNum.argvalue++;
				}
			}
		}
	}

	public static void CheckAllRoleIsDie(java.util.Hashtable aRoleTab, java.util.ArrayList<RoleElementData> aTeamReserve, java.util.ArrayList<RoleElementData> aEnemyReserve, RefObject<Byte> aTeamNum, RefObject<Byte> aEnemyNum)
	{
		java.util.ArrayList<String> removePosList = new java.util.ArrayList<String>();
		java.util.ArrayList<RoleElementData> addRoleList = new java.util.ArrayList<RoleElementData>();

		Set<Entry> set=aRoleTab.entrySet();
		for(Entry entry : set)
		{
			RoleElementData rd = (RoleElementData)entry.getValue();

			if(rd.hp<=0)
			{
				if(rd.posID<BattleCmdBuilder.tmpOrder2[0])
				{
					if(aTeamReserve.size()>0)
					{
						aTeamReserve.get(0).posID = rd.posID;
						removePosList.add(""+rd.posID);
						addRoleList.add(aTeamReserve.get(0));
						aTeamReserve.remove(0);
						aTeamNum.argvalue++;
					}
				}
				else
				{
					if(aEnemyReserve.size()>0)
					{
						aEnemyReserve.get(0).posID = rd.posID;
						removePosList.add(""+rd.posID);
						addRoleList.add(aEnemyReserve.get(0));
						aEnemyReserve.remove(0);
						aEnemyNum.argvalue++;
					}
				}
			}
		}

		for(String de : removePosList)
		{
			aRoleTab.remove(de);
		}

		for(RoleElementData de : addRoleList)
		{
			aRoleTab.put(de.posID, de);
		}

	}

//	public static void  BuildOrderList()
//	{
//
//		for(int i=BattleCmdBuilder.teamOrder.GetLowerBound(0);i<=BattleCmdBuilder.teamOrder.GetUpperBound(0);i++)
//		{
//			bool flag = false;
//			short num = 0;
//			
//			for(int j=BattleCmdBuilder.teamOrder.GetLowerBound(1);j<=BattleCmdBuilder.teamOrder.GetUpperBound(1);j++)
//			{
//				if(flag)
//				{
//					int n = BattleCmdBuilder.tmpOrder1[i] - num;
//					//if(n>=BattleCmdBuilder.tmpOrder1[0] && n<BattleCmdBuilder.tmpOrder1[BattleCmdBuilder.tmpOrder1.Length-1])
//					if(n<=0)
//					{
//						n = BattleCmdBuilder.tmpOrder1[i] + num;
//					}
//
//					BattleCmdBuilder.teamOrder[i,j] = n;
//
//					
//					
//					n = BattleCmdBuilder.tmpOrder2[i] - num;
//					//if(n>=BattleCmdBuilder.tmpOrder2[0] && n<BattleCmdBuilder.tmpOrder2[BattleCmdBuilder.tmpOrder2.Length-1])
//					if(n<=0)
//					{
//						n = BattleCmdBuilder.tmpOrder2[i] + num;
//					}
//
//					BattleCmdBuilder.enemyOrder[i,j] = n;
//					
//				}
//				else
//				{
//					int n = BattleCmdBuilder.tmpOrder1[i] + num;
//					//if(n>=BattleCmdBuilder.tmpOrder1[0] && n<BattleCmdBuilder.tmpOrder1[BattleCmdBuilder.tmpOrder1.Length-1])
//					{
//						BattleCmdBuilder.teamOrder[i,j] = n;
//					}
//					
//					n = BattleCmdBuilder.tmpOrder2[i] + num;
//					//if(n>=BattleCmdBuilder.tmpOrder2[0] && n<BattleCmdBuilder.tmpOrder2[BattleCmdBuilder.tmpOrder2.Length-1])
//					{
//						BattleCmdBuilder.enemyOrder[i,j] = n;
//					}
//				}
//
//				num++;
//				flag = !flag;
//			}
//		}
//
//	}

	public static void ScanTarget(byte aTargetType, byte aTargetCount, RoleElementData aRole, java.util.Hashtable aRoleTab, RefObject<java.util.ArrayList<RoleElementData>> rdTarget)
	{
//        
//		自己	1
//		己方全体	2
//		己方当前生命值最少	3
//		己方全体随机	4
//		己方除自己外随机	5
//		敌方全体	6
//		敌方全体随机	7
//		被攻击者	8
//		攻击者	9
//		 

		byte type = aTargetType;
		byte count = aTargetCount;

		byte[] tOrder;
		byte[] eOrder;
		if(aRole.posID<BattleCmdBuilder.tmpOrder2[0])
		{
			tOrder = tmpOrder1;
			eOrder = tmpOrder2;
		}
		else
		{
			tOrder = tmpOrder2;
			eOrder = tmpOrder1;
		}

		switch(type)
		{
		case 1:
		case 9:
			{
				rdTarget.argvalue.add(aRole);
			}
			break;
		case 2:
			{
				for(int i=0;i<tOrder.length;i++)
				{
					int key = (int)tOrder[i];
					RoleElementData trd = (RoleElementData)aRoleTab.get(key);
					if(trd!=null && trd.hp>0)
					{
						rdTarget.argvalue.add(trd);
					}
				}
			}
			break;
		case 3:
			{
				RoleElementData trd = null;
				int hp = Integer.MAX_VALUE;
				for(int i=0;i<tOrder.length;i++)
				{
					int key = (int)tOrder[i];
					RoleElementData tmpTrd = (RoleElementData)aRoleTab.get(key);
					if(tmpTrd!=null && tmpTrd.hp>0 && tmpTrd.hp<hp)
					{
						trd = tmpTrd;
						hp = tmpTrd.hp;
					}
				}

				if(trd!=null)
				{
					rdTarget.argvalue.add(trd);
				}
			}
			break;
		case 4:
			{
				for(int i=0;i<tOrder.length;i++)
				{
					int key = (int)tOrder[i];
					RoleElementData trd = (RoleElementData)aRoleTab.get(key);
					if(trd!=null && trd.hp>0)
					{
						rdTarget.argvalue.add(trd);
					}
				}

				while(rdTarget.argvalue.size()>count)
				{
					int n = AttackController.Instance().Rnd(0, rdTarget.argvalue.size());
					rdTarget.argvalue.remove(n);
				}
			}
			break;
		case 5:
			{
				for(int i=0;i<tOrder.length;i++)
				{
					if(tOrder[i]!=aRole.posID)
					{
						int key = (int)tOrder[i];
						RoleElementData trd = (RoleElementData)aRoleTab.get(key);
						if(trd!=null && trd.hp>0)
						{
							rdTarget.argvalue.add(trd);
						}
					}
				}

				while(rdTarget.argvalue.size()>count)
				{
					int n = AttackController.Instance().Rnd(0, rdTarget.argvalue.size());
					rdTarget.argvalue.remove(n);
				}
			}
			break;
		case 6:
			{
				for(int i=0;i<eOrder.length;i++)
				{
					int key = (int)eOrder[i];
					RoleElementData trd = (RoleElementData)aRoleTab.get(key);
					if(trd!=null && trd.hp>0)
					{
						rdTarget.argvalue.add(trd);
					}
				}
			}
			break;
		case 7:
			{
				for(int i=0;i<eOrder.length;i++)
				{
					int key = (int)eOrder[i];
					RoleElementData trd = (RoleElementData)aRoleTab.get(key);
					if(trd!=null && trd.hp>0)
					{
						rdTarget.argvalue.add(trd);
					}
				}

				while(rdTarget.argvalue.size()>count)
				{
					int n = AttackController.Instance().Rnd(0, rdTarget.argvalue.size());
					rdTarget.argvalue.remove(n);
				}
			}
			break;
		case 8:
			{
				if(aRole.posID<BattleCmdBuilder.tmpOrder2[0])
				{
					for(int i=0;i<=BattleCmdBuilder.enemyOrder[aRole.posID-1].length - 1;i++)
					{
						int id = BattleCmdBuilder.enemyOrder[aRole.posID-1][i];
						RoleElementData trd = (RoleElementData)aRoleTab.get((new Integer(id)).toString());
						if(trd!=null && trd.hp>0)
						{
							rdTarget.argvalue.add(trd);
							break;
						}
					}
				}
				else
				{
					for(int i=0;i<=BattleCmdBuilder.teamOrder[aRole.posID-6].length - 1;i++)
					{
						int id = BattleCmdBuilder.teamOrder[aRole.posID-6][i];
						RoleElementData trd = (RoleElementData)aRoleTab.get((new Integer(id)).toString());
						if(trd!=null && trd.hp>0)
						{
							rdTarget.argvalue.add(trd);
							break;
						}
					}
				}
			}
			break;
		}

	}


	public static void CheckOverallBuff(java.util.Hashtable<String,RoleElementData> aRoleTab)
	{
		//天赋属于这里
		Enumeration<RoleElementData> enumerator = aRoleTab.elements();
		while (enumerator.hasMoreElements())
		{
			RoleElementData rd = (RoleElementData)(enumerator.nextElement());
			for(int i=0;i<rd.passiveSkill.size();i++)
			{
				for(int j=0;j<rd.passiveSkill.get(i).buff.size();j++)
				{
					BuffElementData bf = rd.passiveSkill.get(i).buff.get(j);

					if(AttackController.Instance().BuffValid(bf.bufferOdds)) //成功率计算结果
					{
						rd.curBuffList.add(bf);
						BuffHandle(rd, bf);
						bf.bufferRound--;
					}
				}
			}

		}
	}

	public static void CheckTempBuff(RoleElementData aRole, RefObject<CmdElementData> aCmd)
	{
		//1.每回合持续递增或递减
		//2.数值固定，坚持数回合

		for(int i=0;i<aRole.curBuffList.size();i++)
		{
			BuffElementData bf = aRole.curBuffList.get(i);
			if(bf.bufferRound>0)
			{
				if(bf.bufferIsOne==0)
				{
					if(bf.bufferRound==bf.bufferMaxRound)
					{
						BuffHandle(aRole, bf);
					}
				}
				else
				{
					BuffHandle(aRole, bf);
				}

				bf.bufferRound--;
			}
			else
			{
				BuffRevoke(aRole, bf);

				aCmd.argvalue.buffIdRemove.add(bf.bufferEffectID);
				aCmd.argvalue.buffRemoveTarget.add((byte)aRole.posID);
				//aRole.curBuffList.RemoveAt(i);
			}
		}
	}

	public static void CheckPassiveSkill(RoleElementData aRole, java.util.Hashtable aRoleTab, byte[] aTrigger, CmdElementData aCmd)
	{
		//根据触发条件 触发身上的buff
		for(int i=0;i<aRole.curBuffList.size();i++)
		{
			for(int j=0;j<aTrigger.length;j++)
			{
				if(aRole.curBuffList.get(i).bufferTrigger==aTrigger[j])
				{
					BuffHandle(aRole, aRole.curBuffList.get(i));
				}
			}
		}
	}

	public static void BuffHandle(RoleElementData aRole, BuffElementData aBuff)
	{
		//buff影响值施加

		aRole.isStun = false;
		aRole.isCrit = false;
		aRole.isDamageToHp = false;

		switch(aBuff.bufferID)
		{
		case 1:
			{

			aRole.hp+=(aBuff.bufferResult+(aBuff.bufferResult*aRole.addVal.hpAddPer/100));
				if(aRole.hp>aRole.maxHp)
				{
					aRole.hp = aRole.maxHp;
				}
			}
			break;
		case 2:
			{
				aRole.attack += aBuff.bufferResult;
			}
			break;
		case 3:
			{
				aRole.fightDefense += aBuff.bufferResult;
			}
			break;
		case 4:
			{
				aRole.magicDefense += aBuff.bufferResult;
			}
			break;
		case 5:
			{
				float tmpHp = aRole.maxHp * aBuff.bufferResult / 100;
				tmpHp+=(tmpHp * aRole.addVal.hpAddPer / 100);
				aRole.hp+=tmpHp;
				if(aRole.hp>aRole.maxHp)
				{
					aRole.hp = aRole.maxHp;
				}
			}
			break;
		case 6:
			{
				aRole.attack+=(aRole.attackInit * aBuff.bufferResult / 100);
			}
			break;
		case 7:
			{
				aRole.fightDefense+=(aRole.fightDefenseInit * aBuff.bufferResult / 100);
			}
			break;
		case 8:
			{
				aRole.magicDefense+=(aRole.magicDefenseInit * aBuff.bufferResult / 100);
			}
			break;
		case 9:
			{
				aRole.critValue += (short)aBuff.bufferResult;
			}
			break;
		case 10:
			{
				aRole.toughnessValue += (short)aBuff.bufferResult;
			}
			break;
		case 11:
			{
				aRole.missesRate += (short)aBuff.bufferResult;
			}
			break;
		case 12:
			{
				aRole.hitValue += (short)aBuff.bufferResult;
			}
			break;
		case 13:
			{
				aRole.parryValue += (short)aBuff.bufferResult;
			}
			break;
		case 14:
			{
				aRole.piercingValue += (short)aBuff.bufferResult;
			}
			break;
		case 15:
			{
				aRole.rageValue += (byte)aBuff.bufferResult;
			}
			break;
		case 16: //受到伤害减少一定比例
			{
				aRole.addVal.damageSubPer = (byte)aBuff.bufferResult;
			}
			break;
		case 17: //造成的伤害增加一定数值
			{
				aRole.addVal.damageVal = (int)aBuff.bufferResult;
			}
			break;
		case 18: //造成的伤害增加一定比例
			{
				aRole.addVal.damagePer = (byte)aBuff.bufferResult;
			}
			break;
		case 19: //对目标造成一定比例的外功伤害
			{
				aRole.addVal.fightDamagePer = (byte)aBuff.bufferResult;
			}
			break;
		case 20:
			{
				aRole.isStun = true;
			}
			break;
		case 21: //恢复一定比例与自身攻击相关的生命值
			{
				aRole.addVal.addHpFromAttPer = (byte)aBuff.bufferResult;
			}
			break;
		case 22: //受到的伤害转化成生命值
			{
				aRole.isDamageToHp = true;
			}
			break;
		case 23: //降低目标2点怒气
			{
				aRole.addVal.targetSubRage = (byte)aBuff.bufferResult;
			}
			break;
		case 24: //连续普通攻击
			{
				aRole.addVal.comboVal = (byte)aBuff.bufferResult;
			}
			break;
		case 25: //暴击伤害倍数提高一定比例
			{
				aRole.addVal.critMulPer = (byte)aBuff.bufferResult;
			}
			break;
		case 26: //受到外功伤害减少一定比例
			{
				aRole.addVal.fightDamageSubPer = (byte)aBuff.bufferResult;
			}
			break;
		case 27:
			{
				aRole.isCrit = true;
			}
			break;
		case 28: //造成一定比例与敌人生命值上限相关的伤害
			{
				aRole.addVal.damageFromHp = (int)aBuff.bufferResult;
			}
			break;
		case 29: //生命值恢复提高一定比例(已加生命值乘以这个百分比)
			{
				aRole.addVal.hpAddPer = (int)aBuff.bufferResult;
			}
			break;
		case 30:
			{
				aRole.hp-=(aRole.maxHp * aBuff.bufferResult / 100);
			}
			break;
		case 31:
			{
				aRole.maxHp += aBuff.bufferResult;
			}
			break;
		case 32:
			{
				aRole.maxHp += (aRole.maxHp * aBuff.bufferResult / 100);
			}
			break;
		case 33: //对目标造成一定比例的内功伤害
			{
				aRole.addVal.magicDamagePer = (byte)aBuff.bufferResult;
			}
			break;
		case 34: //受到的外功伤害减少一定数值
			{
				aRole.addVal.fightDamageSubVal = (int)aBuff.bufferResult;
			}
			break;
		case 35: //受到的内功伤害减少一定数值
			{
				aRole.addVal.magicDamageSubVal = (int)aBuff.bufferResult;
			}
			break;
		case 36: //受到的伤害减少一定数值
			{
				aRole.addVal.damageSubVal = (int)aBuff.bufferResult;
			}
			break;
		case 37: //造成的外功伤害增加一定数值
			{
				aRole.addVal.fightDamageVal = (int)aBuff.bufferResult;
			}
			break;
		case 38: //造成的内功伤害增加一定数值
			{
				aRole.addVal.magicDamageVal =(int) aBuff.bufferResult;
			}
			break;
		case 39: //收到内功伤害减少一定比例
			{
				aRole.addVal.magicDamageSubPer = (byte)aBuff.bufferResult;
			}
			break;
		case 40: //对男性侠客造成伤害提高一定比例
			{
				aRole.addVal.manDamagePer = (byte)aBuff.bufferResult;
			}
			break;
		case 41: //受到男性侠客伤害减少一定比例
			{
				aRole.addVal.manDamageSubPer = (byte)aBuff.bufferResult;
			}
			break;
		case 42: //对正义阵营侠客伤害提高一定比例
			{
				aRole.addVal.faction1DamagePer = (byte)aBuff.bufferResult;
			}
			break;
		case 43: //受到正义阵营侠客伤害减少一定比例
			{
				aRole.addVal.faction1DamageSubPer = (byte)aBuff.bufferResult;
			}
			break;
		case 44: //对邪恶阵营侠客伤害提高一定比例
			{
				aRole.addVal.faction2DamagePer = (byte)aBuff.bufferResult;
			}
			break;
		case 45: //受到邪恶阵营侠客伤害减少一定比例
			{
				aRole.addVal.faction2DamageSubPer = (byte)aBuff.bufferResult;
			}
			break;
		case 46: //对中立阵营侠客伤害提高一定比例
			{
				aRole.addVal.faction3DamagePer = (byte)aBuff.bufferResult;
			}
			break;
		case 47: //受到中立阵营侠客伤害减少一定比例
			{
				aRole.addVal.faction3DamageSubPer = (byte)aBuff.bufferResult;
			}
			break;
		}
	}

	public static void BuffRevoke(RoleElementData aRole, BuffElementData aBuff)
	{
		//buff影响值去除

		switch(aBuff.bufferID)
		{
		case 1:
			break;
		case 2:
			{
				aRole.attack -= aBuff.bufferResult;
			}
			break;
		case 3:
			{
				aRole.fightDefense -= aBuff.bufferResult;
			}
			break;
		case 4:
			{
				aRole.magicDefense -= aBuff.bufferResult;
			}
			break;
		case 5:
			break;
		case 6:
			{
				aRole.attack-=(aRole.attackInit * aBuff.bufferResult / 100);
			}
			break;
		case 7:
			{
				aRole.fightDefense-=(aRole.fightDefenseInit * aBuff.bufferResult / 100);
			}
			break;
		case 8:
			{
				aRole.magicDefense-=(aRole.magicDefenseInit * aBuff.bufferResult / 100);
			}
			break;
		case 9:
			{
				aRole.critValue -= (short)aBuff.bufferResult;
			}
			break;
		case 10:
			{
				aRole.toughnessValue -= (short)aBuff.bufferResult;
			}
			break;
		case 11:
			{
				aRole.missesRate -= (short)aBuff.bufferResult;
			}
			break;
		case 12:
			{
				aRole.hitValue -= (short)aBuff.bufferResult;
			}
			break;
		case 13:
			{
				aRole.parryValue -= (short)aBuff.bufferResult;
			}
			break;
		case 14:
			{
				aRole.piercingValue -= (short)aBuff.bufferResult;
			}
			break;
		case 15:
			break;
		case 16:
			break;
		case 17:
			break;
		case 18:
			break;
		case 19:
			break;
		case 20:
			break;
		case 21:
			break;
		case 22:
			break;
		case 23:
			break;
		case 24:
			{
				aRole.addVal.comboVal = 0;
			}
			break;
		case 25:
			{
				aRole.addVal.critMulPer = 0;
			}
			break;
		case 26:
			break;
		case 27:
			break;
		case 28:
			break;
		case 29:
			break;
		case 30:
			break;
		case 31:
			break;
		case 32:
			break;
		}
	}
}