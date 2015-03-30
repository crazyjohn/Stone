package com.stone.test.concurrent;

import java.sql.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Teach fish a good way to write concurrent code;
 * 
 * @author crazyjohn
 *
 */
public class FishTest {
	// today recharge info
	ConcurrentHashMap<Date, Integer> todayRechargeInfo = new ConcurrentHashMap<Date, Integer>();
	// need count
	int activityNeedGold = 10;
	// date
	Date todayDate;

	public int getTodayLastRecharge() {
		int result = activityNeedGold - getTodayRechargeAmount();
		return result >= 0 ? result : 0;
	}

	public void addTodayRecharge(int rechargeAmount) {
		this.todayRechargeInfo.put(todayDate, rechargeAmount + getTodayRechargeAmount());
	}

	/**
	 * Get today already recharge amount;
	 * 
	 * @return
	 */
	private int getTodayRechargeAmount() {
		this.todayRechargeInfo.putIfAbsent(todayDate, 0);
		return this.todayRechargeInfo.get(todayDate);
	}

}
