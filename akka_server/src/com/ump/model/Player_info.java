package com.ump.model;

public class Player_info implements Comparable<Player_info> {
	private int player_id;
	private String player_name;
	private short server_id;
	private String username;
	private long last_time;
	private byte db_id;

	public byte getDb_id() {
		return db_id;
	}

	public void setDb_id(byte db_id) {
		this.db_id = db_id;
	}

	public long getLast_time() {
		return last_time;
	}

	public void setLast_time(long last_time) {
		this.last_time = last_time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public String getPlayer_name() {
		return player_name;
	}

	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}

	public short getServer_id() {
		return server_id;
	}

	public void setServer_id(short server_id) {
		this.server_id = server_id;
	}

	@Override
	public int compareTo(Player_info o) {
		return o.getPlayer_id() > getPlayer_id() ? 0 : 1;
	}

}
