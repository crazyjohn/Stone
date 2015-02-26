package com.stone.core.stm.account;

import java.util.Date;

import org.multiverse.api.references.TxnLong;
import org.multiverse.api.references.TxnRef;

import static org.multiverse.api.StmUtils.*;

/**
 * The Java STM implemention;<br>
 * java软件事务内存的实现(ACID); <br>
 * A: atomicity 原子性;<br>
 * C: consistent 一致性；<br>
 * I: isolated 隔离性；<br>
 * 
 * @author crazyjohn
 *
 */
public class GitAccount {
	private final TxnRef<Date> lastModified = newTxnRef();
	private final TxnLong amount = newTxnLong();
	private final String name;

	public GitAccount(final long amount, final String name) {
		this.name = name;
		atomic(new Runnable() {
			@Override
			public void run() {
				GitAccount.this.amount.set(amount);
				lastModified.set(new Date());
				System.out.println(GitAccount.this.name + " init: " + GitAccount.this.amount.get());
			}
		});
	}

	public Date getLastModifiedDate() {
		return lastModified.get();
	}

	public long getAmount() {
		return amount.get();
	}
	
	@Override
	public String toString() {
		return name + ": " + this.amount.get();
	}

	public static void transfer(final GitAccount from, final GitAccount to, final long amount) {
		atomic(new Runnable() {

			@Override
			public void run() {
				Date date = new Date();

				from.lastModified.set(date);
				from.amount.decrement(amount);

				to.lastModified.set(date);
				to.amount.increment(amount);
			}

		});
	}

	public static void main(String[] args) {
		// create from and to
		final GitAccount from = new GitAccount(10, "From");
		final GitAccount to = new GitAccount(20, "To");
		// do tansfer
		GitAccount.transfer(from, to, 5);
		// transaction
		atomic(new Runnable() {
			@Override
			public void run() {
				System.out.println(from);
				System.out.println(to);
			}

		});
	}
}
