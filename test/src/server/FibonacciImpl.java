package server;

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

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
		System.out.println("-----Server Calculating the " + bi + "th Fibonacci number;");
		BigInteger zero = new BigInteger("0");
		BigInteger one = new BigInteger("1");
		if (bi.equals(zero)) {
			return one;
		}
		if (bi.equals(one)) {
			return one;
		}
		
		BigInteger i = one;
		BigInteger low = one;
		BigInteger high = one;
		
		while (i.compareTo(bi) == -1) {
			BigInteger temp = high;
			high = high.add(low);
			low = temp;
			i = i.add(one);
		}
		
		return high;
	}

}
