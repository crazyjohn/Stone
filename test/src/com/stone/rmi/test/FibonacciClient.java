package com.stone.rmi.test;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import server.IFibonacci;

public class FibonacciClient {
	public static void main(String[] args) {
		try {
			IFibonacci fi =  (IFibonacci)Naming.lookup("rmi://192.168.1.104:" + FibonacciServer.PORT + "/fibonacci");
			BigInteger l = fi.getFibonacci(35);
			System.out.println(l);
			
			// new stub
//			FibonacciImpl_Stub stub = new FibonacciImpl_Stub(paramRemoteRef);
//			BigInteger bigInt = stub.getFibonacci(35);
//			System.out.println(bigInt);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
