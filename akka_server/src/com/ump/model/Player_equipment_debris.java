package com.ump.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import com.i4joy.util.JsonHelper;

public class Player_equipment_debris extends DBCache {
	public static final String tableName = "#player_equipment_debris";

	private List<JSONObject> list;

	public List<JSONObject> getPlayer_equipment_debrisList() {
		if (list == null) {
			list = new ArrayList<JSONObject>();
			list.addAll(Arrays.asList(JsonHelper.getObjectArrayFromJson(getDebris_json(), JSONObject.class)));
		}
		return list;
	}

	public void upDate() {
		setDebris_json(JsonHelper.getJsonStringFromCollection(getPlayer_equipment_debrisList()));
	}

	private int player_id;
	private int debris_max_num;
	private String debris_json;

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getDebris_max_num() {
		return debris_max_num;
	}

	public void setDebris_max_num(int debris_max_num) {
		this.debris_max_num = debris_max_num;
	}

	public String getDebris_json() {
		return debris_json;
	}

	public void setDebris_json(String debris_json) {
		this.debris_json = debris_json;
	}

	public Player_equipment_debris() {

	}

	public Player_equipment_debris(Player_equipment_debris ped, byte action) {
		setAction(action);
		this.list = ped.getPlayer_equipment_debrisList();
		this.player_id = ped.getPlayer_id();
		this.debris_max_num = ped.getDebris_max_num();
		this.debris_json = ped.getDebris_json();
	}
	
	public static void main(String[] args) {
		String str = "[]";
		JsonHelper.getObjectArrayFromJson(str, JSONObject.class);
		Arrays.asList(JsonHelper.getObjectArrayFromJson(str, JSONObject.class));
		ArrayList list = new ArrayList<JSONObject>();
		list.addAll(Arrays.asList(JsonHelper.getObjectArrayFromJson(str, JSONObject.class)));
		list.size();
	}

}
