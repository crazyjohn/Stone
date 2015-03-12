package com.stone.rmi.test;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import server.IFibonacci;

public class FibonacciImpl extends UnicastRemoteObject implements IFibonacci {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 5584366321564689741L;

	protected FibonacciImpl() throws RemoteException {
		super();
	}

	@Override
	public BigInteger getFibonacci(int i) throws RemoteException {
		return this.getFibonacci(new BigInteger(Long.toString(i)));
	}

	@Override
	public BigInteger getFibonacci(BigInteger bi) throws RemoteException {
		return null;
	}

}
