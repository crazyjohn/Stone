package com.i4joy.akka.kok.monster.other.vip;

public class VipGiftTemplet {
	private int id;
	private String name;
	private int original_price;
	private int reduction_price;
	private long[][] items;
	private String desc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOriginal_price() {
		return original_price;
	}

	public void setOriginal_price(int original_price) {
		this.original_price = original_price;
	}

	public int getReduction_price() {
		return reduction_price;
	}

	public void setReduction_price(int reduction_price) {
		this.reduction_price = reduction_price;
	}

	public long[][] getItems() {
		return items;
	}

	public void setItems(long[][] items) {
		this.items = items;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
