package com.ump.model;

public class Player_emails extends DBCache{
	public static final String tableName = "#player_emails";
	private int id;
	private int player_id;
	private String context;
	private String sender_name;
	private String item_json;
	private byte status;
	private byte go;
	private byte type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getSender_name() {
		return sender_name;
	}

	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}

	public String getItem_json() {
		return item_json;
	}

	public void setItem_json(String item_json) {
		this.item_json = item_json;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public byte getGo() {
		return go;
	}

	public void setGo(byte go) {
		this.go = go;
	}

}
