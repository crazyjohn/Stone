package com.ump.model;

public class Servers {
	private int id;
	private String server_name;
	private int online_num;
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServer_name() {
		return server_name;
	}

	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}

	public int getOnline_num() {
		return online_num;
	}

	public void setOnline_num(int online_num) {
		this.online_num = online_num;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
