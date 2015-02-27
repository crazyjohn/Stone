package com.ump.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.json.JSONObject;

import com.i4joy.util.JsonHelper;

public class Player_items extends DBCache{
//	@Override
//	public String getTableName() {
//		return "#player_items";
//	}
	public static final String tableName = "#player_items";

	private List<JSONObject> list;

	public List<JSONObject> getItemList() {
		if (list == null) {
			list = new ArrayList<JSONObject>();
			list.addAll(Arrays.asList(JsonHelper.getObjectArrayFromJson(getItem_json(), JSONObject.class)));
		}
		return list;
	}
	
	public void upDate()
	{
		setItem_json(JsonHelper.getJsonStringFromCollection(getItemList()));
	}
	
	private int player_id;
	private int  item_max_num;
	private String item_json;
	public int getPlayer_id() {
		return player_id;
	}
	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	public int getItem_max_num() {
		return item_max_num;
	}
	public void setItem_max_num(int item_max_num) {
		this.item_max_num = item_max_num;
	}
	public String getItem_json() {
		return item_json;
	}
	public void setItem_json(String item_json) {
		this.item_json = item_json;
	}
	
	public Player_items()
	{
		
	}
	
	public Player_items(Player_items pi,byte action)
	{
		setAction(action);
		this.player_id = pi.getPlayer_id();
		this.item_max_num = pi.getItem_max_num();
		this.item_json = pi.getItem_json();
		this.list = pi.getItemList();
	}
	
}
