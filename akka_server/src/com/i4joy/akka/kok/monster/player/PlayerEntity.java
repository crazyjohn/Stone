package com.i4joy.akka.kok.monster.player;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import scala.languageFeature.reflectiveCalls;
import akka.actor.ActorRef;

import com.i4joy.akka.kok.Property;
import com.i4joy.akka.kok.camel.worker.LoginWorker;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.RC_playerInfoGet;
import com.i4joy.akka.kok.db.rw.RW_PlayerTableService;
import com.i4joy.akka.kok.monster.ThingsType;
import com.i4joy.akka.kok.monster.service.EnumFails;
import com.i4joy.akka.kok.monster.service.EnumReasons;
import com.i4joy.akka.kok.monster.service.PlayerService;
import com.i4joy.util.JsonHelper;
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
import com.ump.model.Player_mercenary_debirs;
import com.ump.model.Player_info;
import com.ump.model.Player_items;
import com.ump.model.Player_mercenary_gifts;
import com.ump.model.Player_mercenary_record;
import com.ump.model.Player_mercenarys;
import com.ump.model.Player_team;
import com.ump.model.Team;

public class PlayerEntity {
	protected final Log logger = LogFactory.getLog(getClass());

	private String playerName;

	private ActorRef mediator;

	private int playerId;

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public void setPlayerInfo(Player_info playerInfo) {
		this.playerInfo = playerInfo;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public PlayerEntity(){
		
	}

	public PlayerEntity(String playerName, ActorRef mediator, int playerId) {
		this.playerName = playerName;
		this.playerId = playerId;
		this.mediator = mediator;
		this.getPlayerInfo();
		this.getTeams();
		this.getMercenaryList();
		this.test();
	}

	public void test() {
		PlayerService.addExp(this, null, mediator, ActorRef.noSender(), 9999, EnumReasons.GM_ADD_EXP, null);
		PlayerService.give(this, null, mediator, ActorRef.noSender(), 10, EnumReasons.GM_COST_MONEY, "", PlayerService.EnumCurrency.FREE_GOLD, null);
		PlayerService.cost(this, null, mediator, ActorRef.noSender(), 5, EnumReasons.GM_COST_MONEY, "", PlayerService.EnumCurrency.FREE_GOLD, PlayerService.EnumCurrency.RMB_GOLD, null);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 1000003, 2);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 2000003, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 2000003, 3);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 3000003, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 4000003, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 4000003, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 4000004, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 5000004, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 5000004, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 6000004, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 6000004, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 6000003, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 7000003, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 7000003, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 7000004, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 8000004, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 8000004, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 8000005, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 8888, 100000);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 9999, 100000);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 7777, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 6666, 1);
		PlayerService.setTeam(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 1000003, 0, 1);
		PlayerService.setTeam(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 3000003, 0, 1);
		PlayerService.setTeam(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 5000004, 0, 1);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 1000004, 2);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 1000006, 2);
		PlayerService.upgrade(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 1000004, 0, 0, new int[][] { { 1000006, 1 } }, false);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 3000003, 1);
		PlayerService.upgrade(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 3000003, 0, 0, null, true);
		PlayerService.giveItem(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 5000004, 1);
		PlayerService.upgrade(this, null, mediator, ActorRef.noSender(), null, EnumReasons.GM_COST_MONEY, "", 5000004, 0, 0, new int[][] { { 5000004, 1 } }, false);
	}

	// ////////////////////////////////DB START//////////////////////////////
	private String dbName;
	private Player_info playerInfo;
	private Player player;
	private Player_equipment_debris equipmentDebris;
	private Player_mercenarys mercenarys;
	private List<Mercenary> mercenaryList;
	private List<Equipment> equipmentList;
//	private List<Equipment_extra> equipmentExtraList;
	private List<Amulet> amuletList;
	private Player_equipments equipments;
	private Player_mercenary_debirs mercenaryDebris;
	private Player_amulets amulets;
	private Player_items items;
	private Player_amulets_debris amuletDebris;
	private Player_mercenary_gifts mercenaryGifts;
	private List<Team> teams;
	private Player_team teamFriends;
	private Player_mercenary_record playerMerceanryRecord;

	public Mercenary getPlayerMercenary() {
		List<Mercenary> mercenaryList = getMercenaryList();
		for (int i = 0; i < mercenaryList.size(); i++) {
			Mercenary m = mercenaryList.get(i);
			if (m.getBase_id() < 1000003) {// TODO
				return m;
			}
		}
		return null;
	}

	public List<Mercenary> getMercenaryList() {
		if (mercenaryList == null) {
			Player_mercenarys pms = getPlayer_mercenarys();
			pms.setAction(DBCache.SELECT);
			mercenaryList = Tools.doDbQuery(pms, mediator, this.dbName, Mercenary.tableName, List.class);
			if (mercenaryList.size() == 0) {
				Mercenary mercenary = new Mercenary();
				mercenary.setAction(DBCache.INSERT);
				mercenary.setLevel((byte) 1);
				mercenary.setOwner_id(getPlayerId());
				mercenaryList.add(mercenary);
				Tools.doDbQuery(new Mercenary(mercenary, DBCache.INSERT), mediator, this.dbName, Mercenary.tableName);
			} else {
				for (int i = 0; i < pms.getMercenaryList().size(); i++) {
					if (pms.getMercenaryList().get(i).getInt("entityId") == 0) {
						Mercenary mercenary = new Mercenary();
						mercenary.setLevel((byte) 1);
						mercenary.setOwner_id(getPlayerId());
						mercenary.setBase_id(pms.getMercenaryList().get(i).getInt("baseId"));
						mercenaryList.add(mercenary);
					}
				}
			}
		}
		return mercenaryList;
	}

	public void setMercenaryList(List<Mercenary> mercenaryList) {
		this.mercenaryList = mercenaryList;
	}

	public Mercenary getMercenary(long baseId, long entityId) {
		List<Mercenary> list = getMercenaryList();
		for (Mercenary mercenary : list) {
			if (mercenary.getBase_id() == baseId && mercenary.getMercenary_id() == entityId) {
				return mercenary;
			}
		}
		return null;
	}
	
	public Mercenary getMercenary(long entityId) {
		List<Mercenary> list = getMercenaryList();
		for (Mercenary mercenary : list) {
			if (mercenary.getMercenary_id() == entityId) {
				return mercenary;
			}
		}
		return null;
	}

	public List<Equipment> getEquipmentList() {
		if (equipmentList == null) {
			Player_equipments pms = getPlayer_equipments();
			equipmentList = Tools.doDbQuery(new Player_equipments(pms, DBCache.SELECT), mediator, this.dbName, Equipment.tableName, List.class);
			for (int i = 0; i < pms.getPlayer_equipmentsList().size(); i++) {
				if (pms.getPlayer_equipmentsList().get(i).getInt("entityId") == 0) {
					Equipment equipment = new Equipment();
					equipment.setLevel((byte) 1);
					equipment.setOwner_id(getPlayerId());
					equipment.setBase_id(pms.getPlayer_equipmentsList().get(i).getInt("baseId"));
					equipment.setEquipment_json(new JSONObject().toString());
					equipmentList.add(equipment);
				}
			}
		}
		return equipmentList;
	}

//	public List<Equipment_extra> getEquipmentExtraList() {
//		if (equipmentExtraList == null) {
//			Player_equipments pms = getPlayer_equipments();
//			equipmentExtraList = Tools.doDbQuery(new Player_equipments(pms, DBCache.SELECT), mediator, this.dbName, Equipment_extra.tableName, List.class);
//		}
//		return equipmentExtraList;
//	}

	public void setEquipmentList(List<Equipment> equipmentList) {
		this.equipmentList = equipmentList;
	}

	public void setAmuletList(List<Amulet> amuletList) {
		this.amuletList = amuletList;
	}

	public List<Amulet> getAmuletList() {
		if (amuletList == null) {
			Player_amulets pms = getPlayer_amulets();
			amuletList = Tools.doDbQuery(new Player_amulets(pms, DBCache.SELECT), mediator, this.dbName, Amulet.tableName, List.class);
			for (int i = 0; i < pms.getAmuletsList().size(); i++) {
				if (pms.getAmuletsList().get(i).getInt("entityId") == 0) {
					Amulet amulet = new Amulet();
					amulet.setLevel((byte) 1);
					amulet.setOwner_id(getPlayerId());
					amulet.setBase_id(pms.getAmuletsList().get(i).getInt("baseId"));
					amuletList.add(amulet);
				}
			}
		}
		return amuletList;
	}

	public EnumFails isFull(long[] baseIds) {
		for (long l : baseIds) {
			switch (ThingsType.getThingsType(l)) {
			case MERCENARY:
				if (getMercenaryList().size() >= getPlayer_mercenarys().getMercenary_max_num())
					;
				{
					return EnumFails.MERCENARY_FULL;
				}
			case EQUIPMENT:
				if (getEquipmentList().size() >= getPlayer_equipments().getEquipment_max_num()) {
					return EnumFails.EQUIPMENTFULL;
				}
			case EQUIPMENT_PIECE:
				if (getPlayer_equipment_debris().getPlayer_equipment_debrisList().size() >= getPlayer_equipment_debris().getDebris_max_num()) {
					return EnumFails.EQUIPMENTPIRECE;
				}
			case AMULET:
				if (getAmuletList().size() >= getPlayer_amulets().getAmulet_max_num()) {
					return EnumFails.AMULETFULL;
				}
			default:
				return EnumFails.UNFAIL;
			}
		}
		return EnumFails.UNFAIL;
	}

	public void addMercenary(int baseId, int entityId) {
		Mercenary mercenary = new Mercenary();
		mercenary.setBase_id((int) baseId);
		mercenary.setOwner_id(getPlayerId());
		mercenary.setMercenary_id(entityId);
		getMercenaryList().add(mercenary);
	}

	public Mercenary getMercenaryByLevel(int baseId, int level) {
		List<Mercenary> list = getMercenaryList();
		for (Mercenary mercenary : list) {
			if (mercenary.getBase_id() == baseId && mercenary.getLevel() == level) {
				return mercenary;
			}
		}
		return null;
	}

	public void removeMercenary(long baseId, long entityId) {
		boolean b = false;
		List<Mercenary> list = getMercenaryList();
		for (Mercenary mercenary : list) {
			if (mercenary.getBase_id() == baseId && mercenary.getMercenary_id() == entityId) {
				list.remove(mercenary);

				break;
			}
		}
		Player_mercenarys playerMercenarys = getPlayer_mercenarys();
		List<JSONObject> jsons = playerMercenarys.getMercenaryList();
		for (JSONObject jsonObject : jsons) {
			if (jsonObject.getInt("baseId") == baseId && jsonObject.getInt("entityId") == entityId) {
				jsons.remove(jsonObject);
				b = true;
				break;
			}
		}
		if (b) {
			Tools.doDbQuery(new Player_mercenarys(getPlayer_mercenarys(), DBCache.UPDATE), mediator, getDbName(), Player_mercenarys.tableName);
		}
	}

	public void addEquipment(int baseId, int entityId) {
		Equipment equipment = new Equipment();
		equipment.setBase_id((int) baseId);
		equipment.setOwner_id(getPlayerId());
		equipment.setEquipment_id(entityId);
		getEquipmentList().add(equipment);
	}

	public void addAmulet(int baseId, int entityId) {
		Amulet equipment = new Amulet();
		equipment.setBase_id((int) baseId);
		equipment.setOwner_id(getPlayerId());
		equipment.setAmulet_id(entityId);
		getAmuletList().add(equipment);
	}

	public Amulet getAmuletByLevel(int baseId, int level) {
		List<Amulet> list = getAmuletList();
		for (Amulet amulet : list) {
			if (amulet.getBase_id() == baseId && amulet.getLevel() == level) {
				return amulet;
			}
		}
		return null;
	}

	public void removeAmulet(int baseId, long entityId) {
		boolean b = false;
		List<Amulet> list = getAmuletList();
		for (Amulet amulet : list) {
			if (amulet.getBase_id() == baseId && amulet.getAmulet_id() == entityId) {
				list.remove(amulet);

				break;
			}
		}
		Player_amulets playerMercenarys = getPlayer_amulets();
		List<JSONObject> jsons = playerMercenarys.getAmuletsList();
		for (JSONObject jsonObject : jsons) {
			if (jsonObject.getInt("baseId") == baseId && jsonObject.getInt("entityId") == entityId) {
				jsons.remove(jsonObject);
				b = true;
				break;
			}
		}
		if (b) {
			Tools.doDbQuery(new Player_amulets(getPlayer_amulets(), DBCache.UPDATE), mediator, getDbName(), Player_amulets.tableName);
		}
	}

	// public Player_team getTeamFriends() {
	// if (teamFriends == null) {
	// teamFriends = new Player_team();
	// teamFriends.setAction(DBCache.SELECT);
	// teamFriends.setPlayer_id(getPlayerId());
	// Player_team result = Tools.doDbQuery(teamFriends, this.mediator,
	// this.dbName, Player_team.tableName, Player_team.class);
	// if (result.getAction() == DBCache.FAIL) {
	// teamFriends = result;
	// }
	// }
	// return teamFriends;
	// }

	@SuppressWarnings("unchecked")
	public List<Team> getTeams() {
		if (teams == null) {
			Team team = new Team();
			team.setAction(DBCache.SELECT);
			team.setPlayer_id(getPlayerId());
			teams = Tools.doDbQuery(new Team(team, DBCache.SELECT), this.mediator, this.dbName, Team.tableName, ArrayList.class);
			if (teams.size() == 0) {
				team.setAction(DBCache.INSERT);
				team.setPostion(0);
				team.setPlayer_id(getPlayerId());
				teams.add(team);
				Tools.doDbQuery(new Team(team, DBCache.INSERT), mediator, this.dbName, Team.tableName);
			}
		}
		return teams;
	}

	public Player_mercenary_gifts getPlayer_mercenary_gifts() {
		if (mercenaryGifts == null) {
			mercenaryGifts = new Player_mercenary_gifts();
			mercenaryGifts.setAction(DBCache.SELECT);
			mercenaryGifts.setPlayer_id(getPlayerId());
			mercenaryGifts.setMercenary_gifts_json(JsonHelper.getJsonStringFromCollection(new ArrayList<JSONObject>()));
			Player_mercenary_gifts result = Tools.doDbQuery(new Player_mercenary_gifts(mercenaryGifts, DBCache.SELECT), this.mediator, this.dbName, Player_mercenary_gifts.tableName, Player_mercenary_gifts.class);
			if (result.getAction() != DBCache.FAIL) {
				mercenaryGifts = result;
			} else {
				Tools.doDbQuery(new Player_mercenary_gifts(mercenaryGifts, DBCache.INSERT), mediator, getDbName(), Player_mercenary_gifts.tableName);
			}
		}
		return mercenaryGifts;
	}

	public Player_team getTeamFriends() {
		if (teamFriends == null) {
			teamFriends = new Player_team();
			teamFriends.setPlayer_id(getPlayerId());
			Player_team result = Tools.doDbQuery(new Player_team(teamFriends, DBCache.SELECT), mediator, getDbName(), Player_team.tableName, Player_team.class);
			if (result.getAction() != DBCache.FAIL) {
				teamFriends = result;
			} else {
				Tools.doDbQuery(new Player_team(teamFriends, DBCache.INSERT), mediator, getDbName(), Player_team.tableName);
			}
		}
		return teamFriends;
	}

	public Player_amulets_debris getPlayerAmuletsDebris() {
		if (amuletDebris == null) {
			amuletDebris = new Player_amulets_debris();
			amuletDebris.setAction(DBCache.SELECT);
			amuletDebris.setPlayer_id(getPlayerId());
			amuletDebris.setAmulet_debris_json(JsonHelper.getJsonStringFromCollection(new ArrayList<JSONObject>()));
			Player_amulets_debris result = Tools.doDbQuery(new Player_amulets_debris(amuletDebris, DBCache.SELECT), this.mediator, this.dbName, Player_amulets_debris.tableName, Player_amulets_debris.class);
			if (result.getAction() != DBCache.FAIL) {
				amuletDebris = result;
			} else {
				Tools.doDbQuery(new Player_amulets_debris(amuletDebris, DBCache.INSERT), mediator, this.dbName, Player_amulets_debris.tableName);
			}
		}
		return amuletDebris;
	}

	public Player_items getPlayer_items() {
		if (items == null) {
			items = new Player_items();
			items.setAction(DBCache.SELECT);
			items.setPlayer_id(getPlayerId());
			items.setItem_json(JsonHelper.getJsonStringFromCollection(new ArrayList<JSONObject>()));
			Player_items result = Tools.doDbQuery(new Player_items(items, DBCache.SELECT), this.mediator, this.dbName, Player_items.tableName, Player_items.class);
			if (result.getAction() != DBCache.FAIL) {
				items = result;
			} else {
				Tools.doDbQuery(new Player_items(items, DBCache.INSERT), this.mediator, getDbName(), Player_items.tableName);
			}
		}
		return items;
	}

	public JSONObject getItem(long baseId) {
		Player_items pItems = getPlayer_items();
		List<JSONObject> list = pItems.getItemList();
		for (JSONObject jsonObject : list) {
			if (jsonObject.getInt("baseId") == baseId) {
				return jsonObject;
			}
		}
		return null;
	}

	public void removeItems(long baseId, int amount) {
		boolean b = false;
		Player_items pItems = getPlayer_items();
		List<JSONObject> list = pItems.getItemList();
		for (JSONObject jsonObject : list) {
			if (jsonObject.getInt("baseId") == baseId) {
				int num = jsonObject.getInt("num") - amount;
				if (num == 0) {
					list.remove(jsonObject);
				} else {
					jsonObject.put("num", num);
				}
				b = true;
				break;
			}
		}
		if (b) {
			Tools.doDbQuery(new Player_items(pItems, DBCache.UPDATE), mediator, getDbName(), Player_items.tableName);
		}
	}

	public void removeEquipment(long baseId, long entityId) {
		List<Equipment> list = getEquipmentList();
		for (Equipment equipment : list) {
			if (equipment.getBase_id() == baseId && equipment.getEquipment_id() == entityId) {
				list.remove(equipment);
				break;
			}
		}
		Player_equipments pe = getPlayer_equipments();
		List<JSONObject> jsonList = pe.getPlayer_equipmentsList();
		for (JSONObject jsonObject : jsonList) {
			if (jsonObject.getInt("baseId") == baseId && jsonObject.getInt("entityId") == entityId) {
				jsonList.remove(jsonObject);
				break;
			}
		}
		pe.upDate();
		Tools.doDbQuery(new Player_equipments(pe, DBCache.UPDATE), mediator, getDbName(), Player_equipments.tableName);
	}

	public Player_amulets getPlayer_amulets() {
		if (amulets == null) {
			amulets = new Player_amulets();
			amulets.setAction(DBCache.SELECT);
			amulets.setPlayer_id(getPlayerId());
			amulets.setAmulet_json(JsonHelper.getJsonStringFromCollection(new ArrayList<String>()));
			Player_amulets result = Tools.doDbQuery(amulets, this.mediator, this.dbName, Player_amulets.tableName, Player_amulets.class);
			if (result.getAction() != DBCache.FAIL) {
				amulets = result;
			} else {
				amulets.setAmulet_max_num(10);// TODO
				Tools.doDbQuery(new Player_amulets(amulets, DBCache.INSERT), mediator, getDbName(), Player_amulets.tableName);
			}
		}
		return amulets;
	}

	public Player_mercenary_debirs getPlayer_mercenaryDebris() {
		if (mercenaryDebris == null) {
			mercenaryDebris = new Player_mercenary_debirs();
			mercenaryDebris.setAction(DBCache.SELECT);
			mercenaryDebris.setPlayer_id(getPlayerId());
			Player_mercenary_debirs result = Tools.doDbQuery(mercenaryDebris, this.mediator, this.dbName, Player_mercenary_debirs.tableName, Player_mercenary_debirs.class);
			if (result.getAction() != DBCache.FAIL) {
				mercenaryDebris = result;
			} else {
				// PlayerInitDataTempletManager.getInstance().initDataTemplet.get;
				List<JSONObject> list = new ArrayList<JSONObject>();
				mercenaryDebris.setDebirs_json(JsonHelper.getJsonStringFromCollection(list));
				mercenaryDebris.setPlayer_id(getPlayerId());
				mercenaryDebris.setDebirs_max_num(10);// TODO
				mercenaryDebris.setAction(DBCache.INSERT);
				Tools.doDbQuery(new Player_mercenary_debirs(mercenaryDebris, DBCache.INSERT), this.mediator, this.dbName, Player_mercenary_debirs.tableName);
			}
		}
		return mercenaryDebris;
	}

	public Player_equipments getPlayer_equipments() {
		if (equipments == null) {
			equipments = new Player_equipments();
			equipments.setAction(DBCache.SELECT);
			equipments.setPlayer_id(getPlayerId());
			Player_equipments result = Tools.doDbQuery(equipments, this.mediator, this.dbName, Player_equipments.tableName, Player_equipments.class);
			if (result.getAction() != DBCache.FAIL) {
				equipments = result;
			} else {
				equipments = new Player_equipments();
				equipments.setEquipment_max_num(10);// TODO
				equipments.setPlayer_id(getPlayerId());
				equipments.setEquipment_json(JsonHelper.getJsonStringFromCollection(new ArrayList<JSONObject>()));
				equipments.upDate();
				Tools.doDbQuery(new Player_equipments(equipments, DBCache.INSERT), mediator, getDbName(), Player_equipments.tableName);
			}
		}
		return equipments;
	}

	/**
	 * 侠客玩家关系表
	 * 
	 * @return
	 */
	public Player_mercenarys getPlayer_mercenarys() {
		if (mercenarys == null) {// 第一次调用
			mercenarys = new Player_mercenarys();
			mercenarys.setPlayer_id(getPlayerId());
			// 从数据库获得侠客关系
			Player_mercenarys result = Tools.doDbQuery(new Player_mercenarys(mercenarys, DBCache.SELECT), this.mediator, this.dbName, Player_mercenarys.tableName, Player_mercenarys.class);
			if (result.getAction() != DBCache.FAIL) {
				mercenarys = result; // 数据库里有
			} else { // 数据库里没有创建新的
				Mercenary mercenary = new Mercenary();
				mercenary.setOwner_id(getPlayerId());
				mercenary.setBase_id(getPlayer().getBase_id());
				mercenary.setCreated_time(new Date(System.currentTimeMillis()));
				int mercenaryId = Tools.getMercenaryId(mediator);// 获得侠客实体ID
				mercenary.setMercenary_id(mercenaryId);
				Tools.doDbQuery(new Mercenary(mercenary, DBCache.INSERT), mediator, getDbName(), Mercenary.tableName);

				List<JSONObject> list = new ArrayList<JSONObject>();
				JSONObject json = new JSONObject();
				json.put(Property.BASEID, getPlayer().getBase_id());
				json.put(Property.ENTITYID, mercenaryId);
				list.add(json);
				mercenarys.setMercenary_json(JsonHelper.getJsonStringFromCollection(list));
				mercenarys.setPlayer_id(getPlayerId());
				mercenarys.setMercenary_max_num(PlayerInitDataTempletManager.getInstance().getPlayerInitDataTemplet().getMercenaryStoreTop());
				Tools.doDbQuery(new Player_mercenarys(mercenarys, DBCache.INSERT), mediator, getDbName(), Player_mercenarys.tableName);// 更新数据库
			}
		}
		return mercenarys;
	}

	public Player_mercenary_record getPlayerMercenaryRecord() {
		if (playerMerceanryRecord == null) {
			playerMerceanryRecord = new Player_mercenary_record();
			playerMerceanryRecord.setPlayer_id(getPlayerId());
			playerMerceanryRecord.setJson(JsonHelper.getJsonStringFromCollection(new ArrayList<JSONObject>()));
			Tools.doDbQuery(new Player_mercenary_record(playerMerceanryRecord, DBCache.INSERT), mediator, getDbName(), Player_mercenary_record.tableName);
		}
		return playerMerceanryRecord;
	}

	public Player_equipment_debris getPlayer_equipment_debris() {
		if (equipmentDebris == null) {
			equipmentDebris = new Player_equipment_debris();
			equipmentDebris.setPlayer_id(getPlayerId());
			equipmentDebris.setDebris_json(JsonHelper.getJsonStringFromCollection(new ArrayList<JSONObject>()));
			Player_equipment_debris result = Tools.doDbQuery(new Player_equipment_debris(equipmentDebris, DBCache.SELECT), this.mediator, this.dbName, Player_equipment_debris.tableName, Player_equipment_debris.class);
			if (result.getAction() != DBCache.FAIL) {
				equipmentDebris = result;
			} else {
				Tools.doDbQuery(new Player_equipment_debris(equipmentDebris, DBCache.INSERT), mediator, getDbName(), Player_equipment_debris.tableName);
			}
		}
		return equipmentDebris;
	}

	public Player_info getPlayerInfo() {
		if (playerInfo == null) {
			try {
				RC_playerInfoGet p = LoginWorker.getPlayerInfo(playerName, mediator);
				playerInfo = new Player_info();
				playerInfo.setPlayer_id(p.getPlayerId());
				playerInfo.setPlayer_name(p.getPlayerName());
				playerInfo.setServer_id((byte) p.getServerId());
				playerInfo.setUsername(p.getUserName());
				playerInfo.setDb_id((byte) p.getDbId());
				dbName = "user_million" + playerInfo.getDb_id();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return playerInfo;
	}

	public Player getPlayer() {
		if (player == null) {
			player = initPlayer();
		}
		return player;
	}

	private Player initPlayer() {
		Player player = new Player();
		player.setPlayer_id(getPlayerId());
		player.setAction(DBCache.SELECT);
		player = RW_PlayerTableService.doPlayer(player, mediator, dbName);
		if (player.getPlayer_id() == 0) {
			player = new Player();
			player.setPlayer_id(playerId);
			player.setPlayer_name(playerName);
			player.setBase_id(1000001);// TODO
			player.setAction(DBCache.INSERT);
			Player temp = RW_PlayerTableService.doPlayer(player, mediator, dbName);
			if (temp.getAction() == DBCache.FAIL) {
				Tools.printlnInfo("角色名称重复创建失败！", logger);
			}
		}
		return player;
	}

	// ////////////////////////////////DB END//////////////////////////////
	
	public Equipment getEquipment(long id){
		for(Equipment e:this.getEquipmentList()){
			if(e.getEquipment_id()==id){
				return e;
			}
		}
		return null;
	}
	
	public Amulet getAmulet(long id){
		for(Amulet a:this.getAmuletList()){
			if(a.getAmulet_id()==id){
				return a;
			}
		}
		return null;
	}
}
