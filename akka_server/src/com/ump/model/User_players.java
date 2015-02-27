package com.ump.model;

public class User_players {
	private int id;
	private String username;
	private String password;
	private String players_json;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPlayers_json() {
		return players_json;
	}

	public void setPlayers_json(String players_json) {
		this.players_json = players_json;
	}

}
