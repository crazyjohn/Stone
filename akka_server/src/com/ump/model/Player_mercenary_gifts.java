package com.ump.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import com.i4joy.util.JsonHelper;

public class Player_mercenary_gifts extends DBCache {

	private List<JSONObject> list;

	public List<JSONObject> getMercenaryGiftList() {
		if (list == null) {
			list = new ArrayList<JSONObject>();
			list.addAll(Arrays.asList(JsonHelper.getObjectArrayFromJson(getMercenary_gifts_json(), JSONObject.class)));
		}
		return list;
	}
	
	public void upDate()
	{
		setMercenary_gifts_json(JsonHelper.getJsonStringFromCollection(getMercenaryGiftList()));
	}

	public static final String tableName = "#player_mercenary_gifts";
	private int player_id;
	private String mercenary_gifts_json;

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public String getMercenary_gifts_json() {
		return mercenary_gifts_json;
	}

	public void setMercenary_gifts_json(String mercenary_gifts_json) {
		this.mercenary_gifts_json = mercenary_gifts_json;
	}
	
	public Player_mercenary_gifts()
	{
		
	}
	
	public Player_mercenary_gifts(Player_mercenary_gifts pmg,byte action)
	{
		setAction(action);
		this.list  = pmg.getMercenaryGiftList();
		this.mercenary_gifts_json = pmg.getMercenary_gifts_json();
		this.player_id = pmg.getPlayer_id();
	}

}
