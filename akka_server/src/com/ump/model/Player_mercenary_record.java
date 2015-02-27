package com.ump.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import com.i4joy.util.JsonHelper;

public class Player_mercenary_record extends DBCache {

	public static final String tableName = "#player_mercenary_record";

	private List<JSONObject> list;

	public List<JSONObject> getMercenaryRecordList() {
		if (list == null) {
			list = new ArrayList<JSONObject>();
			list.addAll(Arrays.asList(JsonHelper.getObjectArrayFromJson(getJson(), JSONObject.class)));
		}
		return list;
	}

	public void upDate() {
		setJson(JsonHelper.getJsonStringFromCollection(getMercenaryRecordList()));
	}

	private int player_id;
	private String json;

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Player_mercenary_record() {

	}

	public Player_mercenary_record(Player_mercenary_record pmr, byte action) {
		setAction(action);
		this.player_id = pmr.getPlayer_id();
		this.json = pmr.getJson();
	}

}
