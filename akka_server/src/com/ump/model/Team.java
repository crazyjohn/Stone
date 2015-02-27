package com.ump.model;

import java.util.ArrayList;
import java.util.List;

import com.i4joy.akka.kok.monster.amulet.AmuletTempletManager;
import com.i4joy.akka.kok.monster.equipment.EquipmentTempletManager;
import com.i4joy.akka.kok.monster.player.PlayerEntity;

public class Team extends DBCache {
	// @Override
	// public String getTableName() {
	// return "#team";
	// }

	public static final String tableName = "#team";
	private String team_id;
	private int postion;
	private int menceray_id;
	private int player_id;
	private int hat_id;
	private int weapon_id;
	private int necklace_id;
	private int body_id;
	private int book_id;
	private int horse_id;

	public int getPostion() {
		return postion;
	}

	public void setPostion(int postion) {
		this.postion = postion;
	}

	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}

	public int getMenceray_id() {
		return menceray_id;
	}

	public void setMenceray_id(int menceray_id) {
		this.menceray_id = menceray_id;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getHat_id() {
		return hat_id;
	}

	public void setHat_id(int hat_id) {
		this.hat_id = hat_id;
	}

	public int getWeapon_id() {
		return weapon_id;
	}

	public void setWeapon_id(int weapon_id) {
		this.weapon_id = weapon_id;
	}

	public int getNecklace_id() {
		return necklace_id;
	}

	public void setNecklace_id(int necklace_id) {
		this.necklace_id = necklace_id;
	}

	public int getBody_id() {
		return body_id;
	}

	public void setBody_id(int body_id) {
		this.body_id = body_id;
	}

	public int getBook_id() {
		return book_id;
	}

	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}

	public int getHorse_id() {
		return horse_id;
	}

	public void setHorse_id(int horse_id) {
		this.horse_id = horse_id;
	}
	
	public Team()
	{
		
	}

	public Team(Team team,byte action) {
		setAction(action);
		this.team_id = team.getTeam_id();
		this.postion = team.getPostion();
		this.menceray_id = team.getMenceray_id();
		this.player_id = team.getPlayer_id();
		this.hat_id = team.getHat_id();
		this.weapon_id = team.getWeapon_id();
		this.necklace_id = team.getNecklace_id();
		this.body_id = team.getBody_id();
		this.book_id = team.getBook_id();
		this.horse_id = team.getHorse_id();
	}
	
	/**
	 * 获取所有的装备模板	ID
	 * @return
	 */
	public long[] getAllEquipments(PlayerEntity pe){
		List<Long> list=new ArrayList<Long>();
		if(this.hat_id>0){
			Equipment e=pe.getEquipment(this.hat_id);
			list.add((long)e.getBase_id());
		}
		if(this.body_id>0){
			Equipment e=pe.getEquipment(this.body_id);
			list.add((long)e.getBase_id());
		}
		if(this.necklace_id>0){
			Equipment e=pe.getEquipment(this.necklace_id);
			list.add((long)e.getBase_id());
		}
		if(this.weapon_id>0){
			Equipment e=pe.getEquipment(this.weapon_id);
			list.add((long)e.getBase_id());
		}
		long[] l=new long[list.size()];
		for(int i=0;i<l.length;i++){
			l[i]=list.get(i);
		}
		
		return l;
	}
	
	/**
	 * 获取所有的宝物模板ID
	 * @return
	 */
	public long[] getAllAmulets(PlayerEntity pe){
		List<Long> list=new ArrayList<Long>();
		if(this.book_id>0){
			Amulet a=pe.getAmulet(this.book_id);
			list.add((long)a.getBase_id());
		}
		if(this.horse_id>0){
			Amulet a=pe.getAmulet(this.horse_id);
			list.add((long)a.getBase_id());
		}
		
		long[] l=new long[list.size()];
		for(int i=0;i<l.length;i++){
			l[i]=list.get(i);
		}
		
		return l;
	}
	

}
