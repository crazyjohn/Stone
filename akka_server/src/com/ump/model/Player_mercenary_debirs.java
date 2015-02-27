package com.ump.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import com.i4joy.util.JsonHelper;

public class Player_mercenary_debirs extends DBCache {
	// @Override
	// public String getTableName() {
	// return "#player_ghosts";
	// }
	public static final String tableName = "#player_mercenary_debirs";

	private List<JSONObject> list;

	public List<JSONObject> getMercenaryDebris() {
		if (list == null) {
			list = new ArrayList<JSONObject>();
			list.addAll(Arrays.asList(JsonHelper.getObjectArrayFromJson(getDebirs_json(), JSONObject.class)));
		}
		return list;
	}

	public void upDate() {
		setDebirs_json(JsonHelper.getJsonStringFromCollection(getMercenaryDebris()));
	}

	private int player_id;
	private int debirs_max_num;
	private String debirs_json;

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getDebirs_max_num() {
		return debirs_max_num;
	}

	public void setDebirs_max_num(int debirs_max_num) {
		this.debirs_max_num = debirs_max_num;
	}

	public String getDebirs_json() {
		return debirs_json;
	}

	public void setDebirs_json(String debirs_json) {
		this.debirs_json = debirs_json;
	}
	
	public Player_mercenary_debirs()
	{
		
	}

	public Player_mercenary_debirs(Player_mercenary_debirs pd,byte action) {
		setAction(action);
		this.list = pd.getMercenaryDebris();
		this.player_id = pd.getPlayer_id();
		this.debirs_max_num = pd.getDebirs_max_num();
		this.debirs_json = pd.getDebirs_json();
	}

}
