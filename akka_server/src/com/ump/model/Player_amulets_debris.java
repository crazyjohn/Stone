package com.ump.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import com.i4joy.util.JsonHelper;

public class Player_amulets_debris extends DBCache {

	public static final String tableName = "#player_amulets_debris";

	private List<JSONObject> list;

	public List<JSONObject> getPlayerAmuletsDebris() {
		if (list == null) {
			list = new ArrayList<JSONObject>();
			list.addAll(Arrays.asList(JsonHelper.getObjectArrayFromJson(getAmulet_debris_json(), JSONObject.class)));
		}
		return list;
	}

	public void upDate() {
		setAmulet_debris_json(JsonHelper.getJsonStringFromCollection(getPlayerAmuletsDebris()));
	}

	private int player_id;
	private String amulet_debris_json;

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public String getAmulet_debris_json() {
		return amulet_debris_json;
	}

	public void setAmulet_debris_json(String amulet_debris_json) {
		this.amulet_debris_json = amulet_debris_json;
	}

	public Player_amulets_debris() {

	}

	public Player_amulets_debris(Player_amulets_debris pad, byte action) {
		setAction(action);
		this.list = pad.getPlayerAmuletsDebris();
		this.player_id = pad.getPlayer_id();
		this.amulet_debris_json = pad.getAmulet_debris_json();
	}

}
