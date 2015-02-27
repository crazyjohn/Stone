/**
 * 
 */
package com.i4joy.akka.kok.calculator;

import java.util.ArrayList;
import java.util.List;

import com.ump.model.Amulet;
import com.ump.model.Equipment;
import com.ump.model.Mercenary;
import com.ump.model.Team;

/**
 * @author Administrator
 *
 */
public class MercenaryDisignData {
	
	private long mercenaryId;
	
	/**
	 * 强化等级
	 */
	private int level;
	
	/**
	 * 进化等级
	 */
	private int quality;
	
	/**
	 * 情义等级
	 */
	private int friendlyLevel;
	
	/**
	 * 经脉等级
	 */
	private int meridiansLevel;
	
	private List<EquipmentData> eds=new ArrayList<MercenaryDisignData.EquipmentData>();
	
	private List<AmuletData> ads=new ArrayList<MercenaryDisignData.AmuletData>();
	
	private long entityId;

	/**
	 * 
	 */
	public MercenaryDisignData() {
		
	}
	
	public long getMercenaryId() {
		return mercenaryId;
	}

	public void setMercenaryId(long mercenaryId) {
		this.mercenaryId = mercenaryId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getFriendlyLevel() {
		return friendlyLevel;
	}

	public void setFriendlyLevel(int friendlyLevel) {
		this.friendlyLevel = friendlyLevel;
	}

	public int getMeridiansLevel() {
		return meridiansLevel;
	}

	public void setMeridiansLevel(int meridiansLevel) {
		this.meridiansLevel = meridiansLevel;
	}

	public List<EquipmentData> getEds() {
		return eds;
	}

	public void setEds(List<EquipmentData> eds) {
		this.eds = eds;
	}

	public List<AmuletData> getAds() {
		return ads;
	}

	public void setAds(List<AmuletData> ads) {
		this.ads = ads;
	}
	
	public void addEquipment(EquipmentData ed){
		this.eds.add(ed);
	}
	
	public void addAmulet(AmuletData ad){
		this.ads.add(ad);
	}
	
	public long getEntityId() {
		return entityId;
	}

	public void setEntityId(long entityId) {
		this.entityId = entityId;
	}

	public long[] getEquipmentIds(){
		long[] ids=new long[this.eds.size()];
		for(int i=0;i<ids.length;i++){
			ids[i]=this.eds.get(i).getEquipmentId();
		}
		return ids;
	}
	
	public long[] getAmuletIds(){
		long[] ids=new long[this.ads.size()];
		for(int i=0;i<ids.length;i++){
			ids[i]=this.ads.get(i).getAmuletId();
		}
		return ids;
	}
	
	public Team createTeam(int position){
		Team t=new Team();
		t.setMenceray_id((int)this.getEntityId());
		t.setPostion(position);
		t.setBody_id((int)this.eds.get(0).getEntityId());
		t.setWeapon_id((int)this.eds.get(1).getEntityId());
		t.setNecklace_id((int)this.eds.get(2).getEntityId());
		t.setHat_id((int)this.eds.get(3).getEntityId());
		t.setBook_id((int)this.ads.get(0).getEntityId());
		t.setHorse_id((int)this.ads.get(0).getEntityId());
		return t;
	}
	
	public Mercenary createMercenary(){
		Mercenary m=new Mercenary();
		m.setMercenary_id(this.entityId);
		m.setBase_id((int)this.mercenaryId);
		m.setLevel((byte)this.level);
		m.setQuality((byte)this.quality);
		m.setFriendly_level(this.friendlyLevel);
		m.setMeridians_level((byte)this.meridiansLevel);
		
		return m;
	}
	
	public List<Equipment> getEquipments(){
		List<Equipment> es=new ArrayList<Equipment>();
		for(EquipmentData ed:this.eds){
			if(ed.getEquipmentId()>0){
				es.add(ed.createEquipment());
			}
		}
		return es;
	}
	
	public List<Amulet> getAmulets(){
		List<Amulet> as=new ArrayList<Amulet>();
		for(AmuletData ad:this.ads){
			if(ad.getAmuletId()>0){
				as.add(ad.createAmulet());
			}
		}
		return as;
	}
	
	public static class EquipmentData{
		private long equipmentId;
		
		private int level;
		
		private long entityId;
		
		public EquipmentData(long equipmentId, int level,long entityId) {
			this.equipmentId = equipmentId;
			this.level = level;
			this.entityId=entityId;
		}

		public long getEquipmentId() {
			return equipmentId;
		}

		public void setEquipmentId(long equipmentId) {
			this.equipmentId = equipmentId;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}
		
		public long getEntityId() {
			return entityId;
		}

		public void setEntityId(long entityId) {
			this.entityId = entityId;
		}

		public Equipment createEquipment(){
			Equipment e=new Equipment();
			e.setEquipment_id(this.entityId);
			e.setLevel(this.level);
			e.setBase_id((int)this.equipmentId);
			return e;
		}
	}
	
	public static class AmuletData{
		private long amuletId;
		
		private int level;
		
		private int quality;
		
		private long entityId;

		public AmuletData(long amuletId, int level, int quality,long entityId) {
			this.amuletId = amuletId;
			this.level = level;
			this.quality = quality;
			this.entityId=entityId;
		}

		public long getAmuletId() {
			return amuletId;
		}

		public void setAmuletId(long amuletId) {
			this.amuletId = amuletId;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getQuality() {
			return quality;
		}

		public void setQuality(int quality) {
			this.quality = quality;
		}
		
		public long getEntityId() {
			return entityId;
		}

		public void setEntityId(long entityId) {
			this.entityId = entityId;
		}

		public Amulet createAmulet(){
			Amulet a=new Amulet();
			a.setAmulet_id(this.entityId);
			a.setLevel((byte)this.level);
			a.setRefine_level(this.quality);
			a.setBase_id((int)this.amuletId);
			return a;
		}
		
	}

}
