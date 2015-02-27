package com.ump.model;

import java.io.Serializable;

public class Activation_code implements Serializable {
	public Activation_code() {

	}

	private int user_id;
	private int num;

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
