package com.ump.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import com.i4joy.util.JsonHelper;

public class Player_equipments extends DBCache {
	// @Override
	// public String getTableName() {
	// return "#player_equipments";
	// }
	public static final String tableName = "#player_equipments";

	private List<JSONObject> list;

	public List<JSONObject> getPlayer_equipmentsList() {
		if (list == null) {
			list = new ArrayList<JSONObject>();
			list.addAll(Arrays.asList(JsonHelper.getObjectArrayFromJson(getEquipment_json(), JSONObject.class)));
		}
		return list;
	}

	public void upDate() {
		setEquipment_json(JsonHelper.getJsonStringFromCollection(getPlayer_equipmentsList()));
	}

	private int player_id;
	private int equipment_max_num;
	private String equipment_json;

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getEquipment_max_num() {
		return equipment_max_num;
	}

	public void setEquipment_max_num(int equipment_max_num) {
		this.equipment_max_num = equipment_max_num;
	}

	public String getEquipment_json() {
		return equipment_json;
	}

	public void setEquipment_json(String equipment_json) {
		this.equipment_json = equipment_json;
	}

	public Player_equipments() {

	}

	public Player_equipments(Player_equipments pe, byte action) {
		setAction(action);
		this.list = pe.getPlayer_equipmentsList();
		this.player_id = pe.getPlayer_id();
		this.equipment_max_num = pe.getEquipment_max_num();
		this.equipment_json = pe.getEquipment_json();
	}

}
