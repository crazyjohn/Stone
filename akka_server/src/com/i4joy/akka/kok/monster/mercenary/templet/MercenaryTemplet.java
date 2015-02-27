package com.i4joy.akka.kok.monster.mercenary.templet;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.i4joy.akka.kok.monster.item.IdNumPair;
import com.i4joy.akka.kok.monster.item.templet.ItemTemplet;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;

/**
 * 侠客模板
 * @author Administrator
 *
 */
public class MercenaryTemplet implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4452652882684854760L;
	
	private long id;
	
	private String name;
	
	private String desc;

	/**
	 * 星级
	 */
	private int star;
	
	/**
	 * 资质
	 */
	private int quality;
	
	/**
	 * 侠客类型
	 */
	private int mercenaryType;
	
	/**
	 * 情义ID
	 */
	private int friendshipId;
	
	/**
	 * 阵营
	 */
	private int camp;
	
	/**
	 * 门派
	 */
	private String faction;
	
	/**
	 * 性别
	 */
	private int sex;
	
	/**
	 * 魂玉兑换价格
	 */
	private int jadePrice;
	
	/**
	 * 炼化所得魂玉
	 */
	private int jadeFromSacrifice;
	
	/**
	 * 合成所需侠灵数量
	 */
	private IdNumPair composeNeededGhosts;
	
	/**
	 * 是否可进阶
	 */
	private byte isSoar;
	
	/**
	 * 是否可炼化
	 */
	private byte isSacrifice;
	
	/**
	 * ICON
	 */
	private String icon;
	
	/**
	 * 升级消耗类型
	 */
	private int upgradeType;
	
	/**
	 * 次要属性类型
	 */
	private int minorPropType;
	
	/**
	 * 属性列表
	 */
	private HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> propsValue=new HashMap<MercenaryPropertyEnum, MercenaryPropertyValue>();
	
	/**
	 * 普通技能
	 */
	private int normalAbilityId;
	
	/**
	 * 怒气技能
	 */
	private int rageAbilityId;
	
	public byte getIsSoar() {
		return isSoar;
	}
	
	public void setIsSoar(byte isSoar) {
		this.isSoar = isSoar;
	}
	
	public int getStar() {
		return star;
	}
	
	public void setStar(int star) {
		this.star = star;
	}
	
	public int getQuality() {
		return quality;
	}
	
	public void setQuality(int quality) {
		this.quality = quality;
	}
	
	public int getMercenaryType() {
		return mercenaryType;
	}
	
	public void setMercenaryType(int mercenaryType) {
		this.mercenaryType = mercenaryType;
	}
	
	public int getFriendshipId() {
		return friendshipId;
	}
	
	public void setFriendshipId(int friendshipId) {
		this.friendshipId = friendshipId;
	}
	
	public int getCamp() {
		return camp;
	}
	
	public void setCamp(int camp) {
		this.camp = camp;
	}
	
	public String getFaction() {
		return faction;
	}
	
	public void setFaction(String faction) {
		this.faction = faction;
	}
	
	public int getSex() {
		return sex;
	}
	
	public void setSex(int sex) {
		this.sex = sex;
	}
	
	public int getJadePrice() {
		return jadePrice;
	}
	
	public void setJadePrice(int jadePrice) {
		this.jadePrice = jadePrice;
	}
	
	public int getJadeFromSacrifice() {
		return jadeFromSacrifice;
	}
	
	public void setJadeFromSacrifice(int jadeFromSacrifice) {
		this.jadeFromSacrifice = jadeFromSacrifice;
	}

	public void addProperty(MercenaryPropertyValue mpv){
		this.propsValue.put(mpv.getMpe(), mpv);
	}

	public byte getIsSacrifice() {
		return isSacrifice;
	}

	public void setIsSacrifice(byte isSacrifice) {
		this.isSacrifice = isSacrifice;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getUpgradeType() {
		return upgradeType;
	}

	public void setUpgradeType(int upgradeType) {
		this.upgradeType = upgradeType;
	}

	public HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> getPropsValue() {
		return propsValue;
	}

	public void setPropsValue(
			HashMap<MercenaryPropertyEnum, MercenaryPropertyValue> propsValue) {
		this.propsValue = propsValue;
	}

	public int getNormalAbilityId() {
		return normalAbilityId;
	}

	public void setNormalAbilityId(int normalAbilityId) {
		this.normalAbilityId = normalAbilityId;
	}

	public int getRageAbilityId() {
		return rageAbilityId;
	}

	public void setRageAbilityId(int rageAbilityId) {
		this.rageAbilityId = rageAbilityId;
	}

	public IdNumPair getComposeNeededGhosts() {
		return composeNeededGhosts;
	}

	public void setComposeNeededGhosts(IdNumPair composeNeededGhosts) {
		this.composeNeededGhosts = composeNeededGhosts;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Collection<MercenaryPropertyValue> getAllProps(){
		return this.propsValue.values();
	}

	public int getMinorPropType() {
		return minorPropType;
	}

	public void setMinorPropType(int minorPropType) {
		this.minorPropType = minorPropType;
	}
	
}
