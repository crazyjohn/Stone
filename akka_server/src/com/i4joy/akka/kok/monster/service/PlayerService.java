package com.i4joy.akka.kok.monster.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import akka.actor.ActorRef;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.monster.ThingsType;
import com.i4joy.akka.kok.monster.amulet.AmuletTempletManager;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletBasePropertyTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletDebirsTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletRefineTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletUpGradePayTemplet;
import com.i4joy.akka.kok.monster.equipment.EquipmentTempletManager;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentBasePropertyTemplet;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentRefineTemplete;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentUpGradePayTemplet;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentdDebrisTemplet;
import com.i4joy.akka.kok.monster.item.IdNumPair;
import com.i4joy.akka.kok.monster.item.ItemTempletManager;
import com.i4joy.akka.kok.monster.item.templet.ItemRewardTemplet;
import com.i4joy.akka.kok.monster.item.templet.ItemTemplet;
import com.i4joy.akka.kok.monster.mercenary.MercenaryTempletManager;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryFriendlyDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryGhostTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryGiftTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryMeridiansTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryQualityDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryUpgradeDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.TeamTemplet;
import com.i4joy.akka.kok.monster.player.PlayerEntity;
import com.i4joy.akka.kok.protobufs.KOKPacket.PlayerResponse;
import com.i4joy.akka.kok.protobufs.KOKPacket.Secret;
import com.i4joy.akka.kok.protobufs.ProtobufFactory;
import com.i4joy.util.Tools;
import com.ump.model.Amulet;
import com.ump.model.DBCache;
import com.ump.model.Equipment;
import com.ump.model.Equipment_extra;
import com.ump.model.Mercenary;
import com.ump.model.Player;
import com.ump.model.Player_amulets;
import com.ump.model.Player_amulets_debris;
import com.ump.model.Player_equipment_debris;
import com.ump.model.Player_equipments;
import com.ump.model.Player_items;
import com.ump.model.Player_mercenary_debirs;
import com.ump.model.Player_mercenary_gifts;
import com.ump.model.Player_mercenary_record;
import com.ump.model.Player_mercenarys;
import com.ump.model.Player_team;
import com.ump.model.Team;

public class PlayerService {
	protected final Log logger = LogFactory.getLog(getClass());

	private static final byte GET = 0;// 获取属性
	private static final byte SET = 1;// 设置属性
	private static final byte BUILD = 2;// 创建协议包
	private static final byte COST = 3;// 扣除属性

	public enum EnumCurrency {
		/**
		 * 游戏币
		 */
		MONEY(1), RMB_GOLD(2), FREE_GOLD(3), REPUTATION(5), STAMINA_PVP(6), STAMINA_PVE(7), YU(8), MERCENARY_EXP(9), STAR(10);

		final int index;

		private EnumCurrency(int index) {
			this.index = index;
		}

		public static EnumCurrency get(int index) {
			EnumCurrency[] ecs = values();
			for (EnumCurrency ec : ecs) {
				if (ec.index == index) {
					return ec;
				}
			}
			return null;
		}
	}

	/**
	 * 添加修改玩家属性
	 * 
	 * @param action
	 * @param playerEntity
	 * @param ec
	 * @param value
	 * @param builder
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> T doAction(byte action, PlayerEntity playerEntity, EnumCurrency ec, int value, PlayerResponse.Builder builder, Class<T> clazz) {
		Object o = null;
		switch (ec) {
		case MONEY:
			if (action == SET) {
				playerEntity.getPlayer().setMoney(playerEntity.getPlayer().getMoney() + value);
			} else if (action == GET) {
				o = playerEntity.getPlayer().getMoney();
			} else if (action == COST) {
				playerEntity.getPlayer().setMoney(playerEntity.getPlayer().getMoney() - value);
			} else if (action == BUILD && builder != null) {
				builder.setMoney(value);
				o = builder;
			}
			break;
		case RMB_GOLD:
			if (action == SET) {
				playerEntity.getPlayer().setRmb_gold(playerEntity.getPlayer().getRmb_gold() + value);
			} else if (action == GET) {
				o = playerEntity.getPlayer().getRmb_gold();
			} else if (action == COST) {
				playerEntity.getPlayer().setRmb_gold(playerEntity.getPlayer().getRmb_gold() - value);
			} else if (action == BUILD && builder != null) {
				builder.setGold(value);
				o = builder;
			}
		case FREE_GOLD:
			if (action == SET) {
				playerEntity.getPlayer().setFree_gold(playerEntity.getPlayer().getFree_gold() + value);
			} else if (action == GET) {
				o = playerEntity.getPlayer().getFree_gold();
			} else if (action == COST) {
				playerEntity.getPlayer().setFree_gold(playerEntity.getPlayer().getFree_gold() - value);
			} else if (action == BUILD && builder != null) {
				builder.setGold(value);
				o = builder;
			}
			break;

		case REPUTATION:
			if (action == SET) {
				playerEntity.getPlayer().setReputation(playerEntity.getPlayer().getReputation() + value);
			} else if (action == GET) {
				o = playerEntity.getPlayer().getReputation();
			} else if (action == COST) {
				playerEntity.getPlayer().setReputation(playerEntity.getPlayer().getReputation() - value);
			} else if (action == BUILD && builder != null) {
				builder.setReputation(value);
				o = builder;
			}
			break;
		case STAMINA_PVP:
			if (action == SET) {
				playerEntity.getPlayer().setStamina_pvp(playerEntity.getPlayer().getStamina_pvp() + value);
			} else if (action == GET) {
				o = playerEntity.getPlayer().getStamina_pvp();
			} else if (action == COST) {
				playerEntity.getPlayer().setStamina_pvp(playerEntity.getPlayer().getStamina_pvp() - value);
			} else if (action == BUILD && builder != null) {
				builder.setStaminaPvp(value);
				o = builder;
			}
			break;
		case STAMINA_PVE:
			if (action == SET) {
				playerEntity.getPlayer().setStamina_pve(playerEntity.getPlayer().getStamina_pve() + value);
			} else if (action == GET) {
				o = playerEntity.getPlayer().getStamina_pve();
			} else if (action == COST) {
				playerEntity.getPlayer().setStamina_pve(playerEntity.getPlayer().getStamina_pve() - value);
			} else if (action == BUILD && builder != null) {
				builder.setStaminaPve(value);
				o = builder;
			}
			break;
		case YU:
			if (action == SET) {
				playerEntity.getPlayer().setYu(playerEntity.getPlayer().getYu() + value);
			} else if (action == GET) {
				o = playerEntity.getPlayer().getYu();
			} else if (action == COST) {
				playerEntity.getPlayer().setYu(playerEntity.getPlayer().getYu() - value);
			} else if (action == BUILD && builder != null) {
				builder.setYu(value);
				o = builder;
			}
			break;
		case MERCENARY_EXP:
			if (action == SET) {
				playerEntity.getPlayer().setMercenary_exp(playerEntity.getPlayer().getMercenary_exp() + value);
			} else if (action == GET) {
				o = playerEntity.getPlayer().getMercenary_exp();
			} else if (action == COST) {
				playerEntity.getPlayer().setMercenary_exp(playerEntity.getPlayer().getMercenary_exp() - value);
			} else if (action == BUILD && builder != null) {
				builder.setMercenaryExp(value);
				o = builder;
			}
			break;
		case STAR:
			if (action == SET) {
				playerEntity.getPlayer().setStar_num(playerEntity.getPlayer().getStar_num() + value);
			} else if (action == GET) {
				o = playerEntity.getPlayer().getStar_num();
			} else if (action == COST) {
				playerEntity.getPlayer().setStar_num(playerEntity.getPlayer().getStar_num() - value);
			} else if (action == BUILD && builder != null) {
				builder.setStarNum(value);
				o = builder;
			}
			break;
		default:
			break;
		}
		return (T) o;
	}

	/**
	 * 操作结束处理
	 * 
	 * @param playerEntity
	 * @param main
	 * @param sender
	 * @param mediator
	 * @param self
	 * @param secret
	 * @param b
	 */
	private static void endDo(PlayerEntity playerEntity, EnumCurrency main, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, boolean b) {
		if (sender != null && secret != null) {
			if (b) {// 下发协议给客户端
				PlayerResponse.Builder builder = PlayerResponse.newBuilder();
				builder = doAction(BUILD, playerEntity, main, doAction(GET, playerEntity, main, 0, null, Integer.class), builder, PlayerResponse.Builder.class);
				builder.setSercet(secret);
				Tools.sendPacket(builder.build().toByteArray(), sender, self, ProtobufFactory.PLAYER);
			} else {
				Tools.sendPacket(Tools.getErrorResponse(ProtobufFactory.moneyNotEnough), sender, self, ProtobufFactory.ERROR);
			}

		}
		if (b && mediator != null) {// 更新数据库
			Tools.doDbQuery(new Player(playerEntity.getPlayer(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player.tableName);
		}
	}

	/**
	 * 消耗属性
	 * 
	 * @param playerEntity
	 * @param sender
	 * @param mediator
	 * @param self
	 * @param amount
	 * @param reason
	 * @param detal
	 * @param main
	 * @param assist
	 * @param secret
	 * @return false:数量不够
	 */
	public static boolean cost(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, int amount, EnumReasons reason, String detal, EnumCurrency main, EnumCurrency assist, Secret secret) {
		boolean b = true;
		int mainEC = doAction(GET, playerEntity, main, 0, null, Integer.class);
		int assistEC = 0;
		if (assist != null) {
			assistEC = doAction(GET, playerEntity, assist, 0, null, Integer.class);
		}
		b = mainEC + assistEC >= amount;// 主货币和辅货币总数
		if (b) {
			doAction(COST, playerEntity, main, amount > mainEC ? mainEC : amount, null, null);// 先扣主货币
			if (assistEC != 0 && amount > mainEC) {// 主货币不够 扣辅货币
				doAction(COST, playerEntity, assist, amount - mainEC, null, null);
			}
		}
		endDo(playerEntity, main, sender, mediator, self, secret, b);
		return b;
	}

	/**
	 * 修改玩家属性
	 * 
	 * @param playerEntity
	 * @param sender
	 * @param mediator
	 * @param self
	 * @param amount
	 * @param reason
	 * @param detal
	 * @param main
	 * @param secret
	 * @return
	 */
	public static boolean give(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, int amount, EnumReasons reason, String detal, EnumCurrency main, Secret secret) {
		doAction(SET, playerEntity, main, amount, null, null);
		endDo(playerEntity, main, sender, mediator, self, secret, true);
		return true;
	}

	/**
	 * 玩家升级
	 * 
	 * @param playerEntity
	 * @param sender
	 * @param mediator
	 * @param self
	 * @param amount
	 * @param reason
	 * @param secret
	 * @return
	 */
	public static EnumFails addExp(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, int amount, EnumReasons reason, Secret secret) {
		Mercenary mercenary = playerEntity.getPlayerMercenary();// 获得玩家的侠客
		if (mercenary == null) {
			return EnumFails.PLAYERMERCENARY;
		}
		int curExp = mercenary.getExp();// 当前经验
		int curLevel = mercenary.getLevel();// 当前等级
		int oldLevel = curLevel;
		MercenaryTemplet template = MercenaryTempletManager.getInstance().getMercenaryTemplet(playerEntity.getPlayer().getBase_id());
		if (template == null) {
			return EnumFails.MERCENARYTEMPLATE;
		}
		MercenaryUpgradeDataTemplet mudt;
		while (amount > 0) {
			mudt = MercenaryTempletManager.getInstance().getUpgradeDataTemplet(template, curLevel);
			if (mudt == null) {
				return EnumFails.MERCENARYUPGRADE;
			} else {
				int tmp = curExp + amount - mudt.getExpNeeded();
				if (tmp > 0) {
					amount -= (mudt.getExpNeeded() - curExp);
					curExp = 0;
					curLevel++;
				} else {
					if (tmp < 0) {
						curExp += amount;
					} else {
						curExp = 0;
						curLevel++;
					}
					amount = 0;
				}
			}
		}
		mercenary.setLevel((byte) curLevel);// 设置等级
		mercenary.setExp(curExp);// 设置经验
		if (sender != null && secret != null) {// 给客户端发协议
			PlayerResponse.Builder builder = PlayerResponse.newBuilder();
			builder.setExp(curExp);
			if (oldLevel != curLevel)
				builder.setLevel(curLevel);
			builder.setSercet(secret);
			Tools.sendPacket(builder.build().toByteArray(), sender, self, ProtobufFactory.PLAYER);

		}
		if (mediator != null) {// 更新数据库
			Tools.doDbQuery(new Mercenary(mercenary, DBCache.UPDATE), mediator, playerEntity.getDbName(), Mercenary.tableName);
		}
		return EnumFails.UNFAIL;
	}

	/**
	 * 叠加放东西
	 * 
	 * @param list
	 * @param baseId
	 * @param amount
	 */
	private static void upDateOverlap(List<JSONObject> list, long baseId, int amount) {
		boolean had = false;
		for (int i = 0; i < list.size(); i++) {
			JSONObject json = list.get(i);
			if (json.getLong("baseId") == baseId) {
				had = true;
				json.put("num", json.getInt("num") + amount);
				break;
			}
		}
		if (!had) {
			JSONObject json = new JSONObject();
			json.put("baseId", baseId);
			json.put("num", amount);
			list.add(json);
		}
	}

	/**
	 * 不叠加放东西
	 * 
	 * @param list
	 * @param baseId
	 * @param amount
	 */
	private static void upDateUnOverLap(List<JSONObject> list, long baseId, int amount) {
		for (int i = 0; i < amount; i++) {
			JSONObject json = new JSONObject();
			json.put("baseId", baseId);
			json.put("entityId", 0);
			list.add(json);
		}
	}

	/**
	 * 给东西 (侠客，侠客碎片，装备，装备碎片，宝物，宝物碎片，道具，情谊礼物)
	 * 
	 * @param playerEntity
	 * @param sender
	 * @param mediator
	 * @param self
	 * @param secret
	 * @param reason
	 * @param detal
	 * @param baseId
	 *            东西ID
	 * @param amount
	 *            东西数量
	 * @return
	 */
	public static EnumFails giveItem(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal, long baseId, int amount) {
		EnumFails en = playerEntity.isFull(new long[] { baseId });
		if (en != EnumFails.UNFAIL) {// 判断如果要加一个物品是否背包会满
			return en;
		}
		int type = (int) baseId / 1000000;// 东西类型
		List<JSONObject> list;
		switch (type) {
		case 1:// 侠客
			list = playerEntity.getPlayer_mercenarys().getMercenaryList();// 从侠客关系表获得侠客列表
			upDateUnOverLap(list, baseId, amount);// 侠客放入侠客关系表
			if (mediator != null) {
				playerEntity.getPlayer_mercenarys().upDate();
				// 保持玩家侠客关系
				Tools.doDbQuery(new Player_mercenarys(playerEntity.getPlayer_mercenarys(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_mercenarys.tableName);
				playerEntity.addMercenary((int) baseId, 0);// 侠客加入侠客背包
				Player_mercenary_record pmr = playerEntity.getPlayerMercenaryRecord();// 获得拥有侠客情意记录
				List<JSONObject> recordList = pmr.getMercenaryRecordList();// 侠客情意记录列表
				// 判断是否已经拥有
				boolean hadRecord = false;
				for (JSONObject jsonObject : recordList) {
					if (jsonObject.getInt("baseId") == baseId) {
						hadRecord = true;
						break;
					}
				}
				// 之前没有加入记录
				if (!hadRecord) {
					JSONObject json = new JSONObject();
					json.put(Property.BASEID, baseId);
					json.put(Property.EXP, 0);
					json.put(Property.LEVEL, 0);
					recordList.add(json);
				}
				pmr.upDate();// jsonList to string
				// 更新数据库
				Tools.doDbQuery(new Player_mercenary_record(pmr, DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_mercenary_record.tableName);
			}
			break;
		case 2:// 侠客碎片
			list = playerEntity.getPlayer_mercenaryDebris().getMercenaryDebris();// 获得侠客碎片列表
			upDateOverlap(list, baseId, amount);// 侠客碎片加入到侠客列表
			if (mediator != null) {
				playerEntity.getPlayer_mercenaryDebris().upDate();
				// 更新数据库
				Tools.doDbQuery(new Player_mercenary_debirs(playerEntity.getPlayer_mercenaryDebris(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_mercenary_debirs.tableName);
			}
			break;
		case 3:// 装备
			list = playerEntity.getPlayer_equipments().getPlayer_equipmentsList();// 获得装备关系列表
			upDateUnOverLap(list, baseId, amount);// 加入装备关系列表
			if (mediator != null) {
				playerEntity.getPlayer_equipments().upDate();
				// 更新数据库
				Tools.doDbQuery(new Player_equipments(playerEntity.getPlayer_equipments(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_equipments.tableName);
				playerEntity.addEquipment((int) baseId, 0);// 加入装备列表
			}
			break;
		case 4:// 装备碎片
			list = playerEntity.getPlayer_equipment_debris().getPlayer_equipment_debrisList();// 获得装备碎片列表
			upDateOverlap(list, baseId, amount);// 加入装备碎片列表
			if (mediator != null) {
				playerEntity.getPlayer_equipment_debris().upDate();
				// 更新数据库
				Tools.doDbQuery(new Player_equipment_debris(playerEntity.getPlayer_equipment_debris(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_equipment_debris.tableName);
			}
			break;
		case 5:// 宝物
			list = playerEntity.getPlayer_amulets().getAmuletsList();// 获得法宝关系列表
			upDateUnOverLap(list, baseId, amount);// 加入法宝关系列表
			if (mediator != null) {
				playerEntity.getPlayer_amulets().upDate();
				// 更新数据库
				Tools.doDbQuery(new Player_amulets(playerEntity.getPlayer_amulets(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_amulets.tableName);
				playerEntity.addAmulet((int) baseId, 0);// 加入法宝列表
			}
			break;
		case 6:// 宝物碎片
			list = playerEntity.getPlayerAmuletsDebris().getPlayerAmuletsDebris();// 获得法宝碎片关系列表
			upDateOverlap(list, baseId, amount);// 加入法宝碎片关系表
			if (mediator != null) {
				playerEntity.getPlayerAmuletsDebris().upDate();
				// 更新数据库
				Tools.doDbQuery(new Player_amulets_debris(playerEntity.getPlayerAmuletsDebris(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_amulets_debris.tableName);
			}
			break;
		case 7:// 道具
			list = playerEntity.getPlayer_items().getItemList();// 获得道具关系列表
			upDateOverlap(list, baseId, amount);
			if (mediator != null) {
				playerEntity.getPlayer_items().upDate();
				// 更新数据库
				Tools.doDbQuery(new Player_items(playerEntity.getPlayer_items(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_items.tableName);
			}
			break;
		case 8:// 情意礼品
			list = playerEntity.getPlayer_mercenary_gifts().getMercenaryGiftList();// 获得情意礼品关系列表
			upDateOverlap(list, baseId, amount);
			if (mediator != null) {
				playerEntity.getPlayer_mercenary_gifts().upDate();
				// 更新数据库
				Tools.doDbQuery(new Player_mercenary_gifts(playerEntity.getPlayer_mercenary_gifts(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_mercenary_gifts.tableName);
			}
			break;
		default:

			if (baseId == 8888)// 铜钱
			{
				if (amount > 0) {
					give(playerEntity, sender, mediator, self, amount, reason, detal, EnumCurrency.MONEY, secret);
				} else {
					cost(playerEntity, sender, mediator, self, amount, reason, detal, EnumCurrency.MONEY, null, secret);
				}
			} else if (baseId == 9999)// 元宝
			{
				if (amount > 0) {
					give(playerEntity, sender, mediator, self, amount, reason, detal, EnumCurrency.FREE_GOLD, secret);
				} else {
					cost(playerEntity, sender, mediator, self, amount, reason, detal, EnumCurrency.FREE_GOLD, EnumCurrency.RMB_GOLD, secret);
				}
			} else if (baseId == 7777)// 阅历
			{
				if (amount > 0) {
					give(playerEntity, sender, mediator, self, amount, reason, detal, EnumCurrency.MERCENARY_EXP, secret);
				} else {
					cost(playerEntity, sender, mediator, self, amount, reason, detal, EnumCurrency.MERCENARY_EXP, null, secret);
				}
			} else if (baseId == 6666)// 声望
			{
				if (amount > 0) {
					give(playerEntity, sender, mediator, self, amount, reason, detal, EnumCurrency.REPUTATION, secret);
				} else {
					cost(playerEntity, sender, mediator, self, amount, reason, detal, EnumCurrency.REPUTATION, null, secret);
				}
			}
			break;
		}
		return EnumFails.UNFAIL;
	}

	/**
	 * 设置阵容
	 * 
	 * @param playerEntity
	 * @param sender
	 * @param mediator
	 * @param self
	 * @param secret
	 * @param reason
	 * @param detal
	 * @param baseId
	 * @param entityId
	 * @param teamIndex
	 * @return
	 */
	public static EnumFails setTeam(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal, long baseId, long entityId, int teamIndex) {
		boolean b = true;
		TeamTemplet tt = MercenaryTempletManager.getInstance().getTeamTemplet(playerEntity.getPlayerMercenary().getLevel());
		if (tt == null) {
			return EnumFails.TEAMTEMPLATE;
		}
		if (tt.getTeamNum() <= teamIndex) {
			return EnumFails.TEAMTEMPLATENUM;
		}
		Team curTeam = getTeam(playerEntity, mediator, baseId, entityId, teamIndex);// 获得当前阵
		Team oldTeam = getTeam(playerEntity, mediator, baseId, entityId, -1);
		if (curTeam == null) {
			return EnumFails.GETTEAM;
		}
		return setTeam(playerEntity, baseId, entityId, curTeam, mediator, oldTeam);
	}

	/**
	 * 设置阵容
	 * 
	 * @param playerEntity
	 * @param baseId
	 * @param entityId
	 * @param team
	 * @param mediator
	 * @param oldTeam
	 * @return
	 */
	private static EnumFails setTeam(PlayerEntity playerEntity, long baseId, long entityId, Team team, ActorRef mediator, Team oldTeam) {
		boolean b = true;
		int type = (int) baseId / 1000000;
		switch (type) {
		case 1:// 侠客
			if (entityId == 0) {// 没有实体创建实体
				Mercenary m = createMercenaryEntity(playerEntity, baseId, entityId, mediator);
				entityId = m.getMercenary_id();
			}
			team.setMenceray_id((int) entityId);
			if (oldTeam != null) {
				oldTeam.setMenceray_id(0);// 之前的阵去掉
				Tools.doDbQuery(new Team(oldTeam, DBCache.UPDATE), mediator, playerEntity.getDbName(), Team.tableName);
			}
			break;
		case 3:// 装备
			if (entityId == 0) {// 没有实体创建实体
				Equipment e = createEquipmentEntity(playerEntity, baseId, entityId, mediator);
				entityId = e.getEquipment_id();
			}
			EquipmentBasePropertyTemplet ebpt = EquipmentTempletManager.getInstance().getEquipment(baseId);
			if (ebpt != null) {
				byte part = ebpt.getPart();
				switch (part) {
				case 1:// 武器
					team.setWeapon_id((int) entityId);
					if (oldTeam != null) {
						oldTeam.setWeapon_id(0);// 之前的阵去掉
						Tools.doDbQuery(new Team(oldTeam, DBCache.UPDATE), mediator, playerEntity.getDbName(), Team.tableName);
					}
					break;
				case 2:// 头盔
					team.setHat_id((int) entityId);
					if (oldTeam != null) {
						oldTeam.setHat_id(0);// 之前的阵去掉
						Tools.doDbQuery(new Team(oldTeam, DBCache.UPDATE), mediator, playerEntity.getDbName(), Team.tableName);
					}
					break;
				case 3:// 衣服
					team.setBody_id((int) entityId);
					if (oldTeam != null) {
						oldTeam.setBody_id(0);// 之前的阵去掉
						Tools.doDbQuery(new Team(oldTeam, DBCache.UPDATE), mediator, playerEntity.getDbName(), Team.tableName);
					}
					break;
				case 4:// 饰品
					team.setNecklace_id((int) entityId);
					if (oldTeam != null) {
						oldTeam.setNecklace_id(0);// 之前的阵去掉
						Tools.doDbQuery(new Team(oldTeam, DBCache.UPDATE), mediator, playerEntity.getDbName(), Team.tableName);
					}
					break;
				}
			}

			break;
		case 5:// 宝物
			if (entityId == 0) {
				Amulet amulet = createAmuletEntity(playerEntity, baseId, entityId, mediator);
				entityId = amulet.getAmulet_id();
			}
			AmuletBasePropertyTemplet abp = AmuletTempletManager.getInstance().getAmuletBasePropertyTemplet(baseId);
			if (abp == null) {
				return EnumFails.AMULETTEMPLET;
			}
			if (abp.getType() == 1)// 心法
			{
				team.setBook_id((int) entityId);
				if (oldTeam != null) {
					oldTeam.setBook_id(0);// 之前的阵去掉
					Tools.doDbQuery(new Team(oldTeam, DBCache.UPDATE), mediator, playerEntity.getDbName(), Team.tableName);
				}
			} else if (abp.getType() == 2)// 神器
			{
				team.setHorse_id((int) entityId);
				if (oldTeam != null) {
					oldTeam.setHorse_id(0);// 之前的阵去掉
					Tools.doDbQuery(new Team(oldTeam, DBCache.UPDATE), mediator, playerEntity.getDbName(), Team.tableName);
				}
			}
			break;
		default:
			break;
		}
		Tools.doDbQuery(new Team(team, DBCache.UPDATE), mediator, playerEntity.getDbName(), Team.tableName);
		return EnumFails.UNFAIL;
	}

	/**
	 * 获得阵
	 * 
	 * @param playerEntity
	 * @param mediator
	 * @param baseId
	 * @param entityId
	 * @param teamIndex
	 * @return
	 */
	private static Team getTeam(PlayerEntity playerEntity, ActorRef mediator, long baseId, long entityId, int teamIndex) {
		Team team = null;

		List<Team> list = playerEntity.getTeams();// 获得阵营列表
		if (entityId == 0 && teamIndex != -1) {// 如果有阵地实体
			if (teamIndex < list.size()) {// 阵容实体列表里有
				return list.get(teamIndex);// 返回阵
			} else {
				// 如果没有创建阵实体
				team = new Team();
				team.setPlayer_id(playerEntity.getPlayerId());
				team.setPostion(teamIndex);
				Tools.doDbQuery(new Team(team, DBCache.INSERT), mediator, playerEntity.getDbName(), Team.tableName);
				list.add(team);// 新创建的实体加入实体列表
				return team;
			}
		} else {
			// 通过 baseId 和 entityId找实体
			int type = (int) baseId / 1000000;
			switch (type) {
			case 1:// 侠客
				for (Team team2 : list) {
					if (team2.getMenceray_id() == entityId) {
						team = team2;
						break;
					}
				}
				break;
			case 3:// 装备
				for (Team team2 : list) {
					if (team2.getHat_id() == entityId || team2.getBody_id() == entityId || team2.getNecklace_id() == entityId || team2.getWeapon_id() == entityId) {
						team = team2;
						break;
					}
				}
				break;
			case 5:// 宝物
				for (Team team2 : list) {
					if (team2.getBook_id() == entityId || team2.getHorse_id() == entityId) {
						team = team2;
						break;
					}
				}
				break;
			default:

				break;
			}
		}

		return team;
	}

	/**
	 * 升级
	 * 
	 * @param playerEntity
	 * @param sender
	 * @param mediator
	 * @param self
	 * @param secret
	 * @param reason
	 * @param detal
	 * @param baseId
	 * @param entityId
	 * @param amount
	 * @param baseIdAndLevel
	 * @param isEquipmentMax
	 * @return
	 */
	public static EnumFails upgrade(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal, long baseId, long entityId, int amount, int[][] baseIdAndLevel, boolean isEquipmentMax) {
		int exp = 0;
		switch (ThingsType.getThingsType(baseId)) {
		case MERCENARY:
			Mercenary m = createMercenaryEntity(playerEntity, baseId, entityId, mediator);
			exp = 0;
			List<Mercenary> list = null;// 被融化卡片列表
			// 升级需要卡片
			if (baseIdAndLevel != null && baseIdAndLevel.length > 0) {
				list = new ArrayList<Mercenary>();
				for (int[] is : baseIdAndLevel) {
					MercenaryTemplet tmp = MercenaryTempletManager.getInstance().getMercenaryTemplet(is[0]);
					MercenaryUpgradeDataTemplet mudt = MercenaryTempletManager.getInstance().getUpgradeDataTemplet(tmp.getUpgradeType(), is[1]);
					exp += mudt.getExpFromStrengthen();// 累计卡片经验
					list.add(playerEntity.getMercenaryByLevel(is[0], is[1]));
				}
			}
			MercenaryTemplet templet = MercenaryTempletManager.getInstance().getMercenaryTemplet(baseId);
			if (templet == null) {
				return EnumFails.MERCENARYTEMPLATE;
			}
			return merceanryUpgrade(m, exp, templet, playerEntity, mediator, playerEntity.getPlayerMercenary().getLevel(), amount, list);
		case EQUIPMENT:
			Equipment e = createEquipmentEntity(playerEntity, baseId, entityId, mediator);
			EquipmentBasePropertyTemplet equipmentBase = EquipmentTempletManager.getInstance().getEquipment(baseId);
			if (equipmentBase == null) {
				return EnumFails.EQUIPMENTTEMPLET;
			}
			return equipmentUpgrade(e, equipmentBase, playerEntity, isEquipmentMax, mediator);
		case AMULET:
			Amulet amulet = createAmuletEntity(playerEntity, baseId, entityId, mediator);
			exp = 0;
			List<Amulet> amuletList = null;// 使用被融化的法宝卡片
			if (baseIdAndLevel != null && baseIdAndLevel.length > 0) {
				amuletList = new ArrayList<Amulet>();
				for (int[] is : baseIdAndLevel) {
					AmuletBasePropertyTemplet tmp = AmuletTempletManager.getInstance().getAmuletBasePropertyTemplet(is[0]);
					AmuletUpGradePayTemplet mudt = AmuletTempletManager.getInstance().getAmuletUpGradePayTemplet(tmp.getType(), is[1]);
					exp += mudt.getPrice_exp();
					amuletList.add(playerEntity.getAmuletByLevel(is[0], is[1]));
				}
				AmuletBasePropertyTemplet abpt = AmuletTempletManager.getInstance().getAmuletBasePropertyTemplet(baseId);
				if (abpt == null) {
					return EnumFails.AMULETTEMPLET;
				}
				return amuletUpgrade(amulet, exp, abpt, playerEntity, mediator, playerEntity.getPlayerMercenary().getLevel() << 1, amuletList);
			}
			return EnumFails.UPGRADENOCARDS;
		default:
			return EnumFails.UNCATCH;
		}
	}

	/**
	 * 侠客升级
	 * 
	 * @param mercenary
	 * @param exp
	 * @param templet
	 * @param playerEntity
	 * @param mediator
	 * @param maxLevel
	 *            强化最大等级
	 * @param mercenaryExp
	 * @param list
	 * @return
	 */
	private static EnumFails merceanryUpgrade(Mercenary mercenary, int exp, MercenaryTemplet templet, PlayerEntity playerEntity, ActorRef mediator, int maxLevel, int mercenaryExp, List<Mercenary> list) {
		int curLevel = mercenary.getLevel();// 当前等级
		int curExp = mercenary.getExp();// 当前经验
		int needMoney = 0;// 需要钱
		MercenaryUpgradeDataTemplet mudt;
		int amount = 0;
		if (exp > 0) {
			amount = exp;
		} else {
			amount = mercenaryExp;
		}
		while (amount > 0) {
			if (maxLevel < curLevel) {
				return EnumFails.UPGRADEOVERLEVEL;
			}
			mudt = MercenaryTempletManager.getInstance().getUpgradeDataTemplet(templet, curLevel);
			if (mudt == null) {// 如果没有下一级升级的数据
				break;
			} else {
				needMoney += mudt.getFee();// 累计升级需要钱
				int tmp = curExp + amount - mudt.getExpNeeded();
				if (tmp > 0) {
					amount -= (mudt.getExpNeeded() - curExp);
					curExp = 0;
					curLevel++;
				} else {
					if (tmp < 0) {
						curExp += amount;
					} else {
						curExp = 0;
						curLevel++;
					}
					amount = 0;
				}
			}
		}
		if (needMoney > playerEntity.getPlayer().getMoney()) {
			return EnumFails.PLAYERMONEY;
		}
		if (curLevel != mercenary.getLevel()) {
			mercenary.setLevel((byte) curLevel);
		}
		if (curExp != mercenary.getExp()) {
			mercenary.setExp(curExp);
		}
		// 更新数据库
		Tools.doDbQuery(new Mercenary(mercenary, DBCache.UPDATE), mediator, playerEntity.getDbName(), Mercenary.tableName);
		if (exp > 0) {// 删除使用的侠客卡片
			for (Mercenary mercenary2 : list) {
				playerEntity.removeMercenary(mercenary2.getBase_id(), mercenary2.getMercenary_id());
			}

		} else {// 使用阅历强化
			playerEntity.getPlayer().setMercenary_exp(playerEntity.getPlayer().getMercenary_exp() - mercenaryExp);
		}
		playerEntity.getPlayer().setMoney(playerEntity.getPlayer().getMoney() - needMoney);// 减去强化用的钱
		Tools.doDbQuery(new Player(playerEntity.getPlayer(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player.tableName);

		return EnumFails.UNFAIL;

	}

	/**
	 * 装备强化
	 * 
	 * @param equipment
	 * @param equipmentBase
	 * @param playerEntity
	 * @param isMax
	 *            是否强化到最大
	 * @param mediator
	 * @return
	 */
	private static EnumFails equipmentUpgrade(Equipment equipment, EquipmentBasePropertyTemplet equipmentBase, PlayerEntity playerEntity, boolean isMax, ActorRef mediator) {
		int curLevel = equipment.getLevel();// 装备当前等级
		int curMoney = playerEntity.getPlayer().getMoney();// 玩家当前的钱
		int maxLevel = playerEntity.getPlayerMercenary().getLevel() << 1;// 装备强化最大等级
		while (curMoney > 0) {

			EquipmentUpGradePayTemplet eugpt = EquipmentTempletManager.getInstance().getEquipmentUpGradePayTemplet(equipmentBase.getUpgradPayId(), equipment.getLevel());
			if (eugpt == null) {
				return EnumFails.EQUIPMENTUPGRADEPAY;
			}
			if (curMoney >= eugpt.getPay()) {
				curMoney -= eugpt.getPay();
				curLevel++;
				if (curLevel >= maxLevel) {
					break;
				}
			} else {
				break;
			}
			if (!isMax) {
				break;
			}
		}
		if (curMoney != playerEntity.getPlayer().getMoney()) {
			playerEntity.getPlayer().setMoney(curMoney);
			equipment.setLevel((byte) curLevel);
			Tools.doDbQuery(new Player(playerEntity.getPlayer(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player.tableName);
			Tools.doDbQuery(new Equipment(equipment, DBCache.UPDATE), mediator, playerEntity.getDbName(), Equipment.tableName);
		}
		return EnumFails.UNFAIL;
	}

	/**
	 * 法宝强化
	 * 
	 * @param amulet
	 * @param amount
	 * @param templet
	 * @param playerEntity
	 * @param mediator
	 * @param maxLevel
	 *            强化最大等级
	 * @param list
	 * @return
	 */
	private static EnumFails amuletUpgrade(Amulet amulet, int amount, AmuletBasePropertyTemplet templet, PlayerEntity playerEntity, ActorRef mediator, int maxLevel, List<Amulet> list) {
		int curLevel = amulet.getLevel();// 当前法宝等级
		int curExp = amulet.getExp();// 当前法宝经验
		int curMoney = playerEntity.getPlayer().getMoney();
		int needMoney = 0;
		AmuletUpGradePayTemplet mudt;
		while (amount > 0) {
			if (curLevel >= maxLevel) {// 等级大于玩家等级
				return EnumFails.UPGRADEOVERLEVEL;
			}
			mudt = AmuletTempletManager.getInstance().getAmuletUpGradePayTemplet(templet.getType(), curLevel);
			if (mudt == null) {
				return EnumFails.AMULETTEMPLET;
			} else {
				needMoney += mudt.getPay_money();
				int tmp = curExp + amount - mudt.getPay_exp();
				if (tmp > 0) {
					amount -= (mudt.getPay_exp() - curExp);
					curExp = 0;
					curLevel++;
				} else {
					if (tmp < 0) {
						curExp += amount;
					} else {
						curExp = 0;
						curLevel++;
					}
					amount = 0;
				}

			}
		}

		if (needMoney <= curMoney) {
			playerEntity.getPlayer().setMoney(curMoney - needMoney);
			Tools.doDbQuery(new Player(playerEntity.getPlayer(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player.tableName);
			if (curLevel != amulet.getLevel()) {
				amulet.setLevel((byte) curLevel);
			}
			if (curExp != amulet.getExp()) {
				amulet.setExp(curExp);
			}
			for (Amulet amulet2 : list) {// 删除使用的卡片
				playerEntity.removeAmulet(amulet2.getBase_id(), amulet2.getAmulet_id());
			}
			Tools.doDbQuery(new Amulet(amulet, DBCache.UPDATE), mediator, playerEntity.getDbName(), Amulet.tableName);
			return EnumFails.UNFAIL;
		}
		return EnumFails.PLAYERMONEY;// 金钱不够升级
	}

	/**
	 * 进阶
	 * 
	 * @param playerEntity
	 * @param sender
	 * @param mediator
	 * @param self
	 * @param secret
	 * @param reason
	 * @param detal
	 * @param baseId
	 * @param entityId
	 * @param xiLianType
	 * @return
	 */
	public static EnumFails refine(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal, long baseId, long entityId, byte xiLianType) {

		switch (ThingsType.getThingsType(baseId)) {
		case MERCENARY:
			Mercenary m = createMercenaryEntity(playerEntity, baseId, entityId, mediator);// 获得侠客实体
			MercenaryQualityDataTemplet mqdt = MercenaryTempletManager.getInstance().getQualityData(m.getBase_id(), m.getQuality());
			if (mqdt == null) {
				return EnumFails.MERCENARYQUALITY;
			} else {
				List<IdNumPair> list = mqdt.getMaterials();
				long[][] itemIds = new long[list.size()][2];// 进阶需要道具
				IdNumPair pair;
				for (int i = 0; i < list.size(); i++) {
					pair = list.get(i);
					itemIds[i][0] = pair.getId();
					itemIds[i][1] = pair.getNum();
				}
				EnumFails en = refinePay(mqdt.getFee(), playerEntity, itemIds, mediator);
				if (en == EnumFails.UNFAIL) {

					m.setQuality((byte) (m.getQuality() + 1));
					Tools.doDbQuery(new Mercenary(m, DBCache.UPDATE), mediator, playerEntity.getDbName(), Mercenary.tableName);
				}
				return en;
			}
		case AMULET:
			Amulet amulet = createAmuletEntity(playerEntity, baseId, entityId, mediator);// 创建法宝实体
			AmuletRefineTemplet art = AmuletTempletManager.getInstance().getAmuletRefineTemplet(amulet.getBase_id(), amulet.getRefine_level() + 1);
			if (art == null) {
				return EnumFails.AMULETREFINETEMPLET;// 数据表没有下一级进阶
			} else {
				EnumFails en = refinePay(art.getPay_money(), playerEntity, art.getItem_ids(), mediator);
				if (en == EnumFails.UNFAIL) {
					amulet.setRefine_level(amulet.getRefine_level() + 1);
					Tools.doDbQuery(new Amulet(amulet, DBCache.UPDATE), mediator, playerEntity.getDbName(), Amulet.tableName);
				}
				return en;
			}
		case EQUIPMENT:
			Equipment equipment = createEquipmentEntity(playerEntity, baseId, entityId, mediator);// 创建装备实体
			EquipmentBasePropertyTemplet ebpt = EquipmentTempletManager.getInstance().getEquipment(baseId);
			if (ebpt == null) {
				return EnumFails.EQUIPMENTTEMPLET;
			}
			if (!ebpt.isIs_xilian()) {
				return EnumFails.EQUIPMENTUNXILiAN;
			}
			EquipmentRefineTemplete refine = EquipmentTempletManager.getInstance().getEquipmentRefineTemplete(ebpt.getXilian_id(), equipment.getLevel());
			if (refine == null) {
				return EnumFails.EQUIPMENTREFINE;
			}
			JSONObject json = playerEntity.getItem(Property.xilianStoneId);
			if (json == null) {
				return EnumFails.NOHADXILIANSTONE;
			}
			int curXianLianStone = json.getInt(Property.NUM);// 玩家洗练石数量
			int curMoney = playerEntity.getPlayer().getMoney();
			int curGold = playerEntity.getPlayer().getFree_gold() + playerEntity.getPlayer().getRmb_gold();
			int needStone = 0;
			int needMoney = 0;
			int needGold = 0;
			switch (xiLianType) {
			case 0:// 初级洗练
				needStone = 2;
				break;
			case 1:// 中级洗练
				needStone = 1;
				needMoney = 3000;
				break;
			case 2:// 高级洗练
				needStone = 1;
				needGold = 5;
				break;
			}
			if (needStone > curXianLianStone || needMoney > curMoney || needGold > curGold) {
				return EnumFails.UNXILIAN;
			}
			curMoney -= needMoney;
			playerEntity.getPlayer().setMoney(curMoney);
			cost(playerEntity, sender, mediator, self, needGold, reason, detal, EnumCurrency.FREE_GOLD, EnumCurrency.RMB_GOLD, secret);
			playerEntity.removeItems(Property.xilianStoneId, needStone);
			equipment.setUse_stone_num(equipment.getUse_stone_num() + needStone);
			JSONObject extraJson = new JSONObject();
			int hp = refine.getHp(xiLianType);
			if (hp >= 0) {
				if (!extraJson.containsKey("hp")) {
					extraJson.put("hp", hp);
				} else {
					extraJson.put("hp", extraJson.getInt("hp") + hp);
				}
			}
			int ad = refine.getAd(xiLianType);
			if (ad >= 0) {
				if (!extraJson.containsKey("ad")) {
					extraJson.put("ad", ad);
				} else {
					extraJson.put("ad", extraJson.getInt("ad") + ad);
				}
			}
			int adDef = refine.getAdDef(xiLianType);
			if (adDef >= 0) {
				if (!extraJson.containsKey("adDef")) {
					extraJson.put("adDef", adDef);
				} else {
					extraJson.put("adDef", extraJson.getInt("adDef") + adDef);
				}
			}
			int pdDef = refine.getPdDef(xiLianType);
			if (pdDef >= 0) {
				if (!extraJson.containsKey("pdDef")) {
					extraJson.put("pdDef", pdDef);
				} else {
					extraJson.put("pdDef", extraJson.getInt("pdDef") + pdDef);
				}
			}
			int critical = refine.getCritical(xiLianType);
			if (critical >= 0) {
				if (!extraJson.containsKey("critical")) {
					extraJson.put("critical", critical);
				} else {
					extraJson.put("critical", extraJson.getInt("critical") + critical);
				}
			}
			int resilience = refine.getResilience(xiLianType);
			if (resilience >= 0) {
				if (!extraJson.containsKey("resilience")) {
					extraJson.put("resilience", resilience);
				} else {
					extraJson.put("resilience", extraJson.getInt("resilience") + resilience);
				}
			}
			int hitRating = refine.getHitRating(xiLianType);
			if (hitRating >= 0) {
				if (!extraJson.containsKey("hitRating")) {
					extraJson.put("hitRating", hitRating);
				} else {
					extraJson.put("hitRating", extraJson.getInt("hitRating") + hitRating);
				}
			}
			int dodge = refine.getDodge(xiLianType);
			if (dodge >= 0) {
				if (!extraJson.containsKey("dodge")) {
					extraJson.put("dodge", dodge);
				} else {
					extraJson.put("dodge", extraJson.getInt("dodge") + dodge);
				}
			}
			int penetrate = refine.getPenetrate(xiLianType);
			if (penetrate >= 0) {
				if (!extraJson.containsKey("penetrate")) {
					extraJson.put("penetrate", penetrate);
				} else {
					extraJson.put("penetrate", extraJson.getInt("penetrate") + penetrate);
				}
			}
			int block = refine.getBlock(xiLianType);
			if (block >= 0) {
				if (!extraJson.containsKey("block")) {
					extraJson.put("block", block);
				} else {
					extraJson.put("block", extraJson.getInt("block") + block);
				}
			}
			equipment.setJson(extraJson);
			equipment.upDate();
			Tools.doDbQuery(new Equipment(equipment, DBCache.UPDATE), mediator, playerEntity.getDbName(), Equipment.tableName);
		default:
			return EnumFails.UNCATCH;
		}
	}
	/**
	 * 进阶消耗
	 * @param needMoney
	 * @param playerEntity
	 * @param items
	 * @param mediator
	 * @return
	 */
	private static EnumFails refinePay(int needMoney, PlayerEntity playerEntity, long[][] items, ActorRef mediator) {
		if (needMoney > playerEntity.getPlayer().getMoney()) {
			return EnumFails.PLAYERMONEY;// 钱不够
		}
		JSONObject json;
		for (long[] ls : items) {
			json = playerEntity.getItem(ls[0]);
			if (json == null || json.getInt("num") < ls[1]) {
				return EnumFails.PLAYERITEMS;// 没有道具 或者道具数量不足
			}
		}
		for (long[] ls : items) {
			playerEntity.removeItems(ls[0], (int) ls[1]);
		}
		playerEntity.getPlayer().setMoney(playerEntity.getPlayer().getMoney() - needMoney);
		Tools.doDbQuery(new Player(playerEntity.getPlayer(), DBCache.UPDATE), mediator, playerEntity.getDbName(), Player.tableName);
		return EnumFails.UNFAIL;
	}
	/**
	 *  添加情意
	 * @param playerEntity
	 * @param sender
	 * @param mediator
	 * @param self
	 * @param secret
	 * @param reason
	 * @param detal
	 * @param baseId
	 * @param itemBaseId
	 * @return
	 */
	public static boolean addFriendy(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal, long baseId, long itemBaseId) {
		boolean b = false;

		MercenaryTemplet m = MercenaryTempletManager.getInstance().getMercenaryTemplet(baseId);
		if (m == null) {
			return false;
		}

		MercenaryGiftTemplet mgt = MercenaryTempletManager.getInstance().getMercenaryGiftTemplet(itemBaseId);
		if (mgt == null) {
			return false;
		}
		Player_mercenary_record pmr = playerEntity.getPlayerMercenaryRecord();
		List<JSONObject> list = pmr.getMercenaryRecordList();
		JSONObject jsonRecord = null;
		for (JSONObject jsonObject : list) {
			if (jsonObject.getInt("baseId") == baseId) {
				jsonRecord = jsonObject;
				break;
			}
		}

		Player_mercenary_gifts pmg = playerEntity.getPlayer_mercenary_gifts();
		List<JSONObject> giftList = pmg.getMercenaryGiftList();
		JSONObject jsonGift = null;
		for (JSONObject jsonObject : giftList) {
			if (jsonObject.getInt("baseId") == itemBaseId) {
				jsonGift = jsonObject;
				break;
			}
		}

		if (jsonGift == null) {
			return false;
		}

		if (jsonRecord == null) {
			return false;
		}
		MercenaryFriendlyDataTemplet mfdt = MercenaryTempletManager.getInstance().getFriendlyData(m.getFriendshipId(), jsonRecord.getInt("friendLevel") + 1);
		if (mfdt == null) {
			return false;
		}

		int curNum = jsonGift.getInt("num");
		if (curNum <= 0) {
			return false;
		}
		curNum -= 1;
		if (curNum <= 0) {
			giftList.remove(jsonGift);
		} else {
			jsonGift.put("num", curNum);
		}
		Tools.doDbQuery(new Player_mercenary_gifts(pmg, DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_mercenary_gifts.tableName);

		int exp = jsonRecord.getInt("exp");
		exp += mgt.getFriendlyValue();
		if (exp >= mfdt.getExpNeeded()) {
			exp -= mfdt.getExpNeeded();
		}
		jsonRecord.put("exp", exp);
		jsonRecord.put("level", mfdt.getLevel());
		pmr.upDate();
		Tools.doDbQuery(new Player_mercenary_record(pmr, DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_mercenary_record.tableName);

		return b;
	}

	public static EnumFails synthesis(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal, long baseId) {
		EnumFails en = playerEntity.isFull(new long[] { baseId });
		if (en != EnumFails.UNFAIL) {// 判断如果要加一个物品是否背包会满
			return en;
		}
		boolean b = true;
		JSONObject debirsJson = null;
		int curNum = 0;
		switch (ThingsType.getThingsType(baseId)) {
		case MERCENARY_PIECE:
			MercenaryGhostTemplet mg = MercenaryTempletManager.getInstance().getMercenaryGhostTemplet(baseId);
			if (mg == null) {
				return EnumFails.MERCENARYGHOSTE;
			}
			Player_mercenary_debirs pmd = playerEntity.getPlayer_mercenaryDebris();
			List<JSONObject> mercenaryDebirsList = pmd.getMercenaryDebris();

			for (JSONObject jsonObject : mercenaryDebirsList) {
				if (jsonObject.getInt("baseId") == baseId) {
					debirsJson = jsonObject;
					break;
				}
			}

			if (debirsJson == null) {
				return EnumFails.PLAYERMERCENARYGHOSTE;
			}

			MercenaryTemplet m = MercenaryTempletManager.getInstance().getMercenaryTemplet(mg.getMercenaryId());
			if (m == null) {
				return EnumFails.MERCENARYTEMPLATE;
			}

			IdNumPair pair = m.getComposeNeededGhosts();
			curNum = debirsJson.getInt(Property.NUM);
			if (pair.getNum() > curNum) {
				return EnumFails.MERCENARYDEBIRSNUM;
			} else {
				curNum -= pair.getNum();
				if (curNum <= 0) {
					mercenaryDebirsList.remove(debirsJson);
				} else {
					debirsJson.put(Property.NUM, curNum);
				}
				pmd.upDate();
				Tools.doDbQuery(new Player_mercenary_debirs(pmd, DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_mercenary_debirs.tableName);
				giveItem(playerEntity, sender, mediator, self, secret, reason, detal, mg.getMercenaryId(), 1);
			}
			break;
		case EQUIPMENT_PIECE:
			EquipmentdDebrisTemplet edt = EquipmentTempletManager.getInstance().getEquipmentDebirsTemplet(baseId);
			if (edt == null) {
				return EnumFails.EQUIPMENTDEBIRSTEMPLET;
			}
			Player_equipment_debris ped = playerEntity.getPlayer_equipment_debris();
			List<JSONObject> equipmetDebrisList = ped.getPlayer_equipment_debrisList();
			for (JSONObject jsonObject : equipmetDebrisList) {
				if (jsonObject.getInt(Property.BASEID) == baseId) {
					debirsJson = jsonObject;
					break;
				}
			}
			if (debirsJson == null) {
				return EnumFails.PLAYEREQUIPMENTDEBIRSTEMPLET;
			}
			EquipmentBasePropertyTemplet ebpt = EquipmentTempletManager.getInstance().getEquipment(edt.getEquipment_id());
			if (ebpt == null) {
				return EnumFails.EQUIPMENTTEMPLET;
			}
			curNum = debirsJson.getInt("num");
			long[][] eDebirs = ebpt.getDebirs();
			if (eDebirs[0][1] > curNum) {
				return EnumFails.EQUIPMENTYDEBIRSNUM;
			} else {
				curNum -= eDebirs[0][1];
				if (curNum == 0) {
					equipmetDebrisList.remove(debirsJson);
				} else {
					debirsJson.put("num", curNum);
				}
				ped.upDate();
				Tools.doDbQuery(new Player_equipment_debris(ped, DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_equipment_debris.tableName);
				giveItem(playerEntity, sender, mediator, self, secret, reason, detal, edt.getEquipment_id(), 1);
			}
			break;
		case AMULET_PIECE:
			AmuletDebirsTemplet adt = AmuletTempletManager.getInstance().getAmuletDebirsTemplet(baseId);
			if (adt == null) {
				return EnumFails.AMULETDEBIRTEMPLET;
			}
			Player_amulets_debris pad = playerEntity.getPlayerAmuletsDebris();
			List<JSONObject> amuletList = pad.getPlayerAmuletsDebris();
			for (JSONObject jsonObject : amuletList) {
				if (jsonObject.getInt("baseId") == baseId) {
					debirsJson = jsonObject;
					break;
				}
			}
			if (debirsJson == null) {
				return EnumFails.PLAYERAMULETDEBIRSTEMPLET;
			}
			AmuletBasePropertyTemplet abpt = AmuletTempletManager.getInstance().getAmuletBasePropertyTemplet(adt.getAmulet_id());
			if (abpt == null) {
				return EnumFails.AMULETTEMPLET;
			}
			curNum = debirsJson.getInt("num");
			long[][] aDebris = abpt.getDebris_ids();
			if (aDebris[0][1] > curNum) {
				return EnumFails.AMULETYDEBIRSNUM;
			} else {
				curNum -= aDebris[0][1];
				if (curNum == 0) {
					amuletList.remove(debirsJson);
				} else {
					debirsJson.put("num", curNum);
				}
				pad.upDate();
				Tools.doDbQuery(new Player_amulets_debris(pad, DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_amulets_debris.tableName);
				giveItem(playerEntity, sender, mediator, self, secret, reason, detal, adt.getAmulet_id(), 1);
			}
			break;
		default:
			return EnumFails.UNCATCH;
		}
		return EnumFails.UNFAIL;
	}

	public static boolean upgradeMeridians(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal) {
		Player player = playerEntity.getPlayer();
		int curStarNum = player.getStar_num();
		int curMoney = player.getMoney();
		int meridianLevel = player.getMeridians_level();
		MercenaryMeridiansTemplet mmt = MercenaryTempletManager.getInstance().getMeridiansData(meridianLevel);
		int needStar = mmt.getStarsNeeded();
		int needMoney = mmt.getFee();
		if (needStar > curStarNum || curMoney > needMoney) {
			return false;
		}
		player.setMoney(curMoney - needMoney);
		player.setStar_num(curStarNum - needStar);
		player.setMeridians_level(mmt.getLevel());
		Tools.doDbQuery(new Player(player, DBCache.UPDATE), mediator, playerEntity.getDbName(), Player.tableName);
		return true;
	}

	public static boolean setTeamFriend(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal, long baseId, int postion) {
		Player_team pt = playerEntity.getTeamFriends();
		TeamTemplet tt = MercenaryTempletManager.getInstance().getTeamTemplet(playerEntity.getPlayerMercenary().getLevel());
		if (tt == null) {
			return false;
		}
		if (tt.getTeam2Num() <= postion) {
			return false;
		}
		switch (postion) {
		case 0:
			pt.setTeam1_id(postion);
			break;
		case 1:
			pt.setTeam2_id(postion);
			break;
		case 2:
			pt.setTeam3_id(postion);
			break;
		case 3:
			pt.setTeam4_id(postion);
			break;
		case 4:
			pt.setTeam5_id(postion);
			break;
		case 5:
			pt.setTeam6_id(postion);
			break;
		case 6:
			pt.setTeam7_id(postion);
			break;
		case 7:
			pt.setTeam8_id(postion);
			break;
		}
		Tools.doDbQuery(new Player_team(pt, DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_team.tableName);
		return true;
	}

	public static boolean useItem(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal, long baseId) {
		ItemTemplet it = ItemTempletManager.getInstance().getItemTemplet(baseId);
		if (it == null) {
			return false;
		}
		JSONObject item = playerEntity.getItem(baseId);
		if (item == null) {
			return false;
		}
		playerEntity.removeItems(baseId, 1);
		String[] temp = it.getProps().split("_");
		switch (it.getType()) {
		case 1:// 兑换货币
			EnumCurrency ec = EnumCurrency.get(Integer.parseInt(temp[0]));
			give(playerEntity, sender, mediator, self, Integer.parseInt(temp[1]), reason, detal, ec, secret);
			break;
		case 2:// 兑换体力
			give(playerEntity, sender, mediator, self, Integer.parseInt(temp[1]), reason, detal, EnumCurrency.STAMINA_PVE, secret);
			break;
		case 3:// 兑换耐力
			give(playerEntity, sender, mediator, self, Integer.parseInt(temp[1]), reason, detal, EnumCurrency.STAMINA_PVP, secret);
			break;
		case 5:// 宝箱钥匙
			baseId = Long.parseLong(it.getProps());// 取出宝箱ID
		case 4:// 宝箱
			playerEntity.removeItems(baseId, 1);
			ItemRewardTemplet irt = ItemTempletManager.getInstance().getItemRewardTemplet(baseId);
			int quatily = irt.getQuality();
			long[] reward = irt.getReward(quatily);
			giveItem(playerEntity, sender, mediator, self, secret, reason, detal, reward[0], (int) reward[1]);
			break;
		}

		return true;
	}

	public static EnumFails thaw(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal, long baseId, long entityId) {

		switch (ThingsType.getThingsType(baseId)) {
		case MERCENARY:
			MercenaryTemplet mt = MercenaryTempletManager.getInstance().getMercenaryTemplet(baseId);
			if (mt == null) {
				return EnumFails.MERCENARYTEMPLATE;
			}
			if (mt.getIsSacrifice() == 0) {
				return EnumFails.MERCENARYTEMPLATEISA;
			}
			Mercenary m = null;
			List<Mercenary> mercenaryList = playerEntity.getMercenaryList();
			for (Mercenary mercenary : mercenaryList) {
				if (mercenary.getBase_id() == baseId && mercenary.getMercenary_id() == entityId) {
					m = mercenary;
					break;
				}
			}
			if (m == null) {
				return EnumFails.MERCENARY;
			}
			if (m.getQuality() > 1) {
				return EnumFails.MERCENARYQUALITYLEVEL;
			}
			MercenaryUpgradeDataTemplet mudt = MercenaryTempletManager.getInstance().getUpgradeDataTemplet(mt.getUpgradeType(), m.getLevel());
			if (mudt == null) {
				return EnumFails.MERCENARYUPGRADE;
			}
			int jade = mt.getJadeFromSacrifice();
			int mercenary_exp = mudt.getExpFromSacrifice();
			int money = mudt.getMoneyFromSacrifice();
			playerEntity.removeMercenary(m.getBase_id(), m.getMercenary_id());
			give(playerEntity, sender, mediator, self, jade, reason, detal, EnumCurrency.YU, secret);
			give(playerEntity, sender, mediator, self, mercenary_exp, reason, detal, EnumCurrency.MERCENARY_EXP, secret);
			give(playerEntity, sender, mediator, self, money, reason, detal, EnumCurrency.MONEY, secret);
			break;
		case EQUIPMENT:

			EnumFails en = playerEntity.isFull(new long[] { Property.xilianStoneId });
			if (en != EnumFails.UNFAIL) {// 判断如果要加一个物品是否背包会满
				return en;
			}
			List<Equipment> list = playerEntity.getEquipmentList();
			Equipment e = null;
			for (Equipment equipment : list) {
				if (equipment.getBase_id() == baseId) {
					e = equipment;
					break;
				}
			}
			if (e == null) {
				return EnumFails.PLAYEREQUIPMENT;
			}
			EquipmentBasePropertyTemplet ebpt = EquipmentTempletManager.getInstance().getEquipment(baseId);
			int xilianstoneNum = e.getUse_stone_num() + ebpt.getThaw_stone_num();
			playerEntity.removeEquipment(baseId, entityId);
			giveItem(playerEntity, sender, mediator, self, secret, reason, detal, Property.xilianStoneId, xilianstoneNum);
			break;
		default:
			return EnumFails.UNCATCH;
		}
		return EnumFails.UNFAIL;
	}

	public static EnumFails rebirth(PlayerEntity playerEntity, ActorRef sender, ActorRef mediator, ActorRef self, Secret secret, EnumReasons reason, String detal, long baseId, long entityId) {
		MercenaryTemplet mt = MercenaryTempletManager.getInstance().getMercenaryTemplet(baseId);
		if (mt == null || mt.getIsSoar() == 0) {
			return EnumFails.MERCENARYTEMPLATE;
		}
		Mercenary m = playerEntity.getMercenary(baseId, entityId);
		if (m == null || m.getQuality() <= 0) {
			return EnumFails.PLAYERMERCENARY;
		}
		int needGold = playerEntity.getPlayer().getRebirth_num() * 20;
		int curGold = playerEntity.getPlayer().getFree_gold() + playerEntity.getPlayer().getRmb_gold();
		if (curGold < needGold) {
			return EnumFails.PLAYERGOLD;
		}
		List<MercenaryQualityDataTemplet> mqdt = MercenaryTempletManager.getInstance().getLessThenQualityDataList(baseId, m.getQuality());
		if (mqdt == null || mqdt.size() == 0) {
			return EnumFails.MERCENARYQUALITY;
		}
		MercenaryUpgradeDataTemplet mudt = MercenaryTempletManager.getInstance().getUpgradeDataTemplet(mt.getUpgradeType(), m.getLevel() - 1);
		if (mudt == null) {
			return EnumFails.MERCENARYUPGRADE;
		}
		Map<Long, Integer> pairHm = new HashMap<Long, Integer>();
		int qualityMoney = 0;
		for (MercenaryQualityDataTemplet mercenaryQualityDataTemplet : mqdt) {
			List<IdNumPair> pairList = mercenaryQualityDataTemplet.getMaterials();
			qualityMoney += mercenaryQualityDataTemplet.getFee();
			for (IdNumPair idNumPair : pairList) {
				if (pairHm.containsKey(idNumPair.getId())) {
					pairHm.put(idNumPair.getId(), pairHm.get(idNumPair.getId()) + 1);
				} else {
					pairHm.put(idNumPair.getId(), 1);
				}
			}
		}
		long[] keys = new long[pairHm.size()];
		EnumFails en = playerEntity.isFull(keys);
		if (en != EnumFails.UNFAIL) {// 判断如果要加一个物品是否背包会满
			return en;
		}

		int upgradeMoney = mudt.getMoneyFromSacrifice();
		int merceanry_exp = mudt.getExpFromStrengthen();
		playerEntity.removeMercenary(baseId, entityId);
		for (int i = 0; i < keys.length; i++) {
			giveItem(playerEntity, sender, mediator, self, secret, reason, detal, keys[i], pairHm.get(keys[i]));
		}
		give(playerEntity, sender, mediator, self, upgradeMoney + qualityMoney, reason, detal, EnumCurrency.MONEY, secret);
		give(playerEntity, sender, mediator, self, merceanry_exp, reason, detal, EnumCurrency.MERCENARY_EXP, secret);
		cost(playerEntity, sender, mediator, self, needGold, reason, detal, EnumCurrency.FREE_GOLD, EnumCurrency.RMB_GOLD, secret);
		return EnumFails.UNFAIL;
	}

	private static Mercenary createMercenaryEntity(PlayerEntity playerEntity, long baseId, long entityId, ActorRef mediator) {
		Mercenary m = null;
		List<Mercenary> list = playerEntity.getMercenaryList();
		for (Mercenary mercenary : list) {
			if (mercenary.getBase_id() == baseId && mercenary.getMercenary_id() == entityId) {
				m = mercenary;
				break;
			}
		}
		if (m != null && m.getMercenary_id() == 0) {
			m.setMercenary_id(Tools.getMercenaryId(mediator));
			m.setOwner_id(playerEntity.getPlayerId());
			m.setCreated_time(new Date(System.currentTimeMillis()));
			m.setUpdate_time(new Date(System.currentTimeMillis()));
			Tools.doDbQuery(new Mercenary(m, DBCache.INSERT), mediator, playerEntity.getDbName(), Mercenary.tableName);
			Player_mercenarys pm = playerEntity.getPlayer_mercenarys();
			for (int i = 0; i < pm.getMercenaryList().size(); i++) {
				if (pm.getMercenaryList().get(i).getInt("baseId") == m.getBase_id() && pm.getMercenaryList().get(i).getInt("entityId") == 0) {
					pm.getMercenaryList().get(i).put("entityId", m.getMercenary_id());
					pm.upDate();
					Tools.doDbQuery(new Player_mercenarys(pm, DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_mercenarys.tableName);
					break;
				}
			}
		}
		return m;
	}

	// private static Equipment_extra createEquipmentExtra(PlayerEntity
	// playerEntity, long baseId, long entityId, ActorRef mediator) {
	// Equipment_extra ee = null;
	// Equipment e = createEquipmentEntity(playerEntity, baseId, entityId,
	// mediator);
	// List<Equipment_extra> list = playerEntity.getEquipmentExtraList();
	// for (Equipment_extra equipment_extra : list) {
	// if (equipment_extra.getEquipment_id() == e.getEquipment_id()) {
	// return equipment_extra;
	// }
	// }
	//
	// ee = new Equipment_extra();
	// ee.setEquipment_id(e.getEquipment_id());
	// ee.setOwner_id(playerEntity.getPlayerId());
	// Tools.doDbQuery(new Equipment_extra(ee, DBCache.INSERT), mediator,
	// playerEntity.getDbName(), Equipment_extra.tableName);
	// list.add(ee);
	// return ee;
	// }

	private static Equipment createEquipmentEntity(PlayerEntity playerEntity, long baseId, long entityId, ActorRef mediator) {
		Equipment e = null;
		List<Equipment> list = playerEntity.getEquipmentList();
		for (Equipment equipment : list) {
			if (equipment.getBase_id() == baseId && equipment.getEquipment_id() == entityId) {
				e = equipment;
				break;
			}
		}
		if (e != null && e.getEquipment_id() == 0) {
			e.setEquipment_id(Tools.getEquipmentId(mediator));
			e.setOwner_id(playerEntity.getPlayerId());
			e.setCreated_time(new Date(System.currentTimeMillis()));
			e.setEquipment_json(new JSONObject().toString());
			Tools.doDbQuery(new Equipment(e, DBCache.INSERT), mediator, playerEntity.getDbName(), Equipment.tableName);
			Player_equipments pe = playerEntity.getPlayer_equipments();
			for (int i = 0; i < pe.getPlayer_equipmentsList().size(); i++) {
				if (pe.getPlayer_equipmentsList().get(i).getInt("baseId") == e.getBase_id() && pe.getPlayer_equipmentsList().get(i).getInt("entityId") == 0) {
					pe.getPlayer_equipmentsList().get(i).put("entityId", e.getEquipment_id());
					pe.upDate();
					Tools.doDbQuery(new Player_equipments(pe, DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_equipments.tableName);
					break;
				}
			}
		}
		return e;
	}

	private static Amulet createAmuletEntity(PlayerEntity playerEntity, long baseId, long entityId, ActorRef mediator) {
		Amulet e = null;
		List<Amulet> list = playerEntity.getAmuletList();
		for (Amulet amulet : list) {
			if (amulet.getBase_id() == baseId && amulet.getAmulet_id() == entityId) {
				e = amulet;
				break;
			}
		}
		if (e != null && e.getAmulet_id() == 0) {
			e.setAmulet_id(Tools.getAmuletId(mediator));
			e.setOwner_id(playerEntity.getPlayerId());
			e.setCreated_time(new Date(System.currentTimeMillis()));
			Tools.doDbQuery(new Amulet(e, DBCache.INSERT), mediator, playerEntity.getDbName(), Amulet.tableName);
			Player_amulets pe = playerEntity.getPlayer_amulets();
			for (int i = 0; i < pe.getAmuletsList().size(); i++) {
				if (pe.getAmuletsList().get(i).getInt("baseId") == e.getBase_id() && pe.getAmuletsList().get(i).getInt("entityId") == 0) {
					pe.getAmuletsList().get(i).put("entityId", e.getAmulet_id());
					pe.upDate();
					Tools.doDbQuery(new Player_amulets(pe, DBCache.UPDATE), mediator, playerEntity.getDbName(), Player_amulets.tableName);
					break;
				}
			}
		}
		return e;
	}
}
