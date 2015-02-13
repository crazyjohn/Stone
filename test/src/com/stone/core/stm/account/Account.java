package com.stone.core.stm.account;

import java.util.Date;

import org.multiverse.api.references.TxnInteger;
import org.multiverse.api.references.TxnRef;

import static org.multiverse.api.StmUtils.*;

public class Account {
	private final TxnRef<Date> lastUpdate;
	private final TxnInteger balance;

	public Account(int balance) {
		this.lastUpdate = newTxnRef(new Date());
		this.balance = newTxnInteger(balance);
	}

	public void incrementBalance(final int amount, final Date date) {
		atomic(new Runnable() {

			@Override
			public void run() {
				balance.increment(amount);
				lastUpdate.set(date);
				if (balance.get() < 0) {
					throw new IllegalStateException("Not enough money.");
				}
			}
		});
	}
	
	public static void transfer(final Account from, final Account to, final int amount) {
		atomic(new Runnable() {

			@Override
			public void run() {
				Date date = new Date();
				from.incrementBalance(-amount, date);
				to.incrementBalance(amount, date);
			}
			
		});
	}

}
