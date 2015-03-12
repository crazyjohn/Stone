package server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class FibonacciServer {
	public static final int PORT = 8888;

	public static void main(String[] args) {
		try {
			FibonacciImpl fi = new FibonacciImpl();
			LocateRegistry.createRegistry(PORT);
			Naming.rebind("rmi://localhost:" + PORT + "/fibonacci", fi);
			System.out.println("Fibonacci Server Ready.");
		} catch (RemoteException e) {
			System.err.println("Exception in FibonacciImpl.main: " + e);
		} catch (MalformedURLException e) {
			System.err.println("MalformedURLException: " + e);
		}
	}
}
