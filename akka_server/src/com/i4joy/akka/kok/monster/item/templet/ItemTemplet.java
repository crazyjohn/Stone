/**
 * 
 */
package com.i4joy.akka.kok.monster.item.templet;

import java.io.Serializable;

/**
 * 道具模板基类
 * 游戏中所有物品模板、侠客模板都需要继承
 * @author Administrator
 *
 */
public class ItemTemplet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2095220975909443867L;
	
	private long id;
	
	private String name;
	
	/**
	 * 点击使用时，跳转到客户端的界面ID
	 */
	private int uiId;
	
	/**
	 * 道具类型
	 */
	private int type;
	
	/**
	 * 附加属性，根据类型不同，具有不同的意义
	 */
	private String props;
	
	/**
	 * 价格
	 */
	private int price;
	
	private String desc;

	/**
	 * 
	 */
	public ItemTemplet() {
		
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUiId() {
		return uiId;
	}

	public void setUiId(int uiId) {
		this.uiId = uiId;
	}

	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
}
