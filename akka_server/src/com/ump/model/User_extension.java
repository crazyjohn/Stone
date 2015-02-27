package com.ump.model;

import java.util.Date;

public class User_extension extends DBCache {
//	@Override
//	public String getTableName() {
//		return "#user_extension";
//	}
	public static final String tableName = "#user_extension";
	private int id;
	private String username;
	private long mobile_id;
	private String email;
	private long client_ip;
	private String client_id;
	private Date create_date;
	private Date last_login_date;
	private String channel;
	private String device;

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

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public long getMobile_id() {
		return mobile_id;
	}

	public void setMobile_id(long mobile_id) {
		this.mobile_id = mobile_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getClient_ip() {
		return client_ip;
	}

	public void setClient_ip(long client_ip) {
		this.client_ip = client_ip;
	}

	public String getClient_id() {
		return client_id;
	}

	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getLast_login_date() {
		return last_login_date;
	}

	public void setLast_login_date(Date last_login_date) {
		this.last_login_date = last_login_date;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

}
