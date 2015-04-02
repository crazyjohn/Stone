package com.stone.test.concurrent.deadlock;

public class DynamicCallTest {
	static class Account {
	}

	public void transfer(Account from, Account to, int amount) {
		synchronized (from) {
			synchronized (to) {
				// do logic
			}
		}
	}
}
