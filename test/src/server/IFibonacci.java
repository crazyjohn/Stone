package server;

import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFibonacci extends Remote {
	public BigInteger getFibonacci(int i) throws RemoteException;
	public BigInteger getFibonacci(BigInteger bi) throws RemoteException;
}
