package com.ump.model;

public class Player extends DBCache {
	public static final String tableName = "#player";
	private int id;
	private int player_id;
	private int base_id;// 侠客基本ID
//	private int stone;
	private int money;
	private int mercenary_exp;// 将魂
	private int yu;// 魂玉
	private int stamina_pve;
	private int stamina_pvp;
	private long out_time;
	private String player_name;
	private int reputation;
	private int stamina_pvp_limit;
	private int rmb_gold;
	private int free_gold;
	private int meridians_level;
	private int star_num;
	private int rebirth_num = 1;
	
	

	public int getRebirth_num() {
		return rebirth_num;
	}

	public void setRebirth_num(int rebirth_num) {
		this.rebirth_num = rebirth_num;
	}

	public int getStar_num() {
		return star_num;
	}

	public void setStar_num(int star_num) {
		this.star_num = star_num;
	}

	public int getMeridians_level() {
		return meridians_level;
	}

	public void setMeridians_level(int meridians_level) {
		this.meridians_level = meridians_level;
	}

	public int getBase_id() {
		return base_id;
	}

	public void setBase_id(int base_id) {
		this.base_id = base_id;
	}

	public int getRmb_gold() {
		return rmb_gold;
	}

	public void setRmb_gold(int rmb_gold) {
		this.rmb_gold = rmb_gold;
	}

	public int getFree_gold() {
		return free_gold;
	}

	public void setFree_gold(int free_gold) {
		this.free_gold = free_gold;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReputation() {
		return reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

	public int getStamina_pvp_limit() {
		return stamina_pvp_limit;
	}

	public void setStamina_pvp_limit(int stamina_pvp_limit) {
		this.stamina_pvp_limit = stamina_pvp_limit;
	}

	public String getPlayer_name() {
		return player_name;
	}

	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

//	public int getStone() {
//		return stone;
//	}
//
//	public void setStone(int stone) {
//		this.stone = stone;
//	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getMercenary_exp() {
		return mercenary_exp;
	}

	public void setMercenary_exp(int mercenary_exp) {
		this.mercenary_exp = mercenary_exp;
	}

	public int getYu() {
		return yu;
	}

	public void setYu(int yu) {
		this.yu = yu;
	}

	public int getStamina_pve() {
		return stamina_pve;
	}

	public void setStamina_pve(int stamina_pve) {
		this.stamina_pve = stamina_pve;
	}

	public int getStamina_pvp() {
		return stamina_pvp;
	}

	public void setStamina_pvp(int stamina_pvp) {
		this.stamina_pvp = stamina_pvp;
	}

	public long getOut_time() {
		return out_time;
	}

	public void setOut_time(long out_time) {
		this.out_time = out_time;
	}

	public Player() {

	}

	public Player(Player player, byte action) {
		setAction(action);
		this.id = player.getId();
		this.player_id = player.getPlayer_id();
		this.base_id = player.getBase_id();
		// this.mercenary_id = player.getMercenary_id();
		this.money = player.getMoney();
		this.mercenary_exp = player.getMercenary_exp();
		this.yu = player.getYu();
		this.stamina_pve = player.getStamina_pve();
		this.stamina_pvp = player.getStamina_pvp();
		this.out_time = player.getOut_time();
		this.player_name = player.getPlayer_name();
		this.reputation = player.getReputation();
		this.stamina_pvp_limit = player.getStamina_pvp_limit();
		this.rmb_gold = player.getRmb_gold();
		this.free_gold = player.getFree_gold();
	}

}
