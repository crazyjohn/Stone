package com.ump.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import com.i4joy.util.JsonHelper;

public class Player_mercenarys extends DBCache {
	// @Override
	// public String getTableName() {
	// return "#player_mercenarys";
	// }
	public static final String tableName = "#player_mercenarys";

	private List<JSONObject> list;

	public List<JSONObject> getMercenaryList() {
		if (list == null) {
			list=new ArrayList<JSONObject>();
			list.addAll(Arrays.asList(JsonHelper.getObjectArrayFromJson(getMercenary_json(), JSONObject.class)));
		}
		return list;
	}

	public void upDate() {
		setMercenary_json(JsonHelper.getJsonStringFromCollection(getMercenaryList()));
	}

	public static String getTablename() {
		return tableName;
	}

	private int player_id;
	private int mercenary_max_num;
	private String mercenary_json;

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getMercenary_max_num() {
		return mercenary_max_num;
	}

	public void setMercenary_max_num(int mercenary_max_num) {
		this.mercenary_max_num = mercenary_max_num;
	}

	public String getMercenary_json() {
		return mercenary_json;
	}

	public void setMercenary_json(String mercenary_json) {
		this.mercenary_json = mercenary_json;
	}

	public Player_mercenarys() {
	}

	public Player_mercenarys(Player_mercenarys pm, byte action) {
		setAction(action);
		this.list = pm.getMercenaryList();
		this.player_id = pm.getPlayer_id();
		this.mercenary_max_num = pm.getMercenary_max_num();
		this.mercenary_json = pm.getMercenary_json();
	}

}
