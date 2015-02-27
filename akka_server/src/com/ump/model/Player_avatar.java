package com.ump.model;

public class Player_avatar extends DBCache {
	public static final String tableName = "#player_avatar";
	private int id;
	private int player_id;
	private byte sex;
	private short body_id;
	private short body_tex_id;
	private short head_id;
	private short head_tex_id;
	private short face_id;
	private short face_tex_id;
	private short cloth_id;
	private short cloth_tex_id;
	private short weapon_id;
	private short weapon_tex_id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	public short getBody_id() {
		return body_id;
	}

	public void setBody_id(short body_id) {
		this.body_id = body_id;
	}

	public short getBody_tex_id() {
		return body_tex_id;
	}

	public void setBody_tex_id(short body_tex_id) {
		this.body_tex_id = body_tex_id;
	}

	public short getHead_id() {
		return head_id;
	}

	public void setHead_id(short head_id) {
		this.head_id = head_id;
	}

	public short getHead_tex_id() {
		return head_tex_id;
	}

	public void setHead_tex_id(short head_tex_id) {
		this.head_tex_id = head_tex_id;
	}

	public short getFace_id() {
		return face_id;
	}

	public void setFace_id(short face_id) {
		this.face_id = face_id;
	}

	public short getFace_tex_id() {
		return face_tex_id;
	}

	public void setFace_tex_id(short face_tex_id) {
		this.face_tex_id = face_tex_id;
	}

	public short getCloth_id() {
		return cloth_id;
	}

	public void setCloth_id(short cloth_id) {
		this.cloth_id = cloth_id;
	}

	public short getCloth_tex_id() {
		return cloth_tex_id;
	}

	public void setCloth_tex_id(short cloth_tex_id) {
		this.cloth_tex_id = cloth_tex_id;
	}

	public short getWeapon_id() {
		return weapon_id;
	}

	public void setWeapon_id(short weapon_id) {
		this.weapon_id = weapon_id;
	}

	public short getWeapon_tex_id() {
		return weapon_tex_id;
	}

	public void setWeapon_tex_id(short weapon_tex_id) {
		this.weapon_tex_id = weapon_tex_id;
	}

}
