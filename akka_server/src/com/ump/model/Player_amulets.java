package com.ump.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import com.i4joy.util.JsonHelper;

public class Player_amulets extends DBCache {
	public static final String tableName = "#player_amulets";

	private List<JSONObject> list;

	public List<JSONObject> getAmuletsList() {
		if (list == null) {
			list = new ArrayList<JSONObject>();
			list.addAll(Arrays.asList(JsonHelper.getObjectArrayFromJson(getAmulet_json(), JSONObject.class)));
		}
		return list;
	}

	public void upDate() {
		setAmulet_json(JsonHelper.getJsonStringFromCollection(getAmuletsList()));
	}

	private int player_id;
	private int amulet_max_num;
	private String amulet_json;

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getAmulet_max_num() {
		return amulet_max_num;
	}

	public void setAmulet_max_num(int amulet_max_num) {
		this.amulet_max_num = amulet_max_num;
	}

	public String getAmulet_json() {
		return amulet_json;
	}

	public void setAmulet_json(String amulet_json) {
		this.amulet_json = amulet_json;
	}

	public Player_amulets() {

	}

	public Player_amulets(Player_amulets pa, byte action) {
		setAction(action);
		this.list = pa.getAmuletsList();
		this.player_id = pa.getPlayer_id();
		this.amulet_max_num = pa.getAmulet_max_num();
		this.amulet_json = pa.getAmulet_json();
	}

}
