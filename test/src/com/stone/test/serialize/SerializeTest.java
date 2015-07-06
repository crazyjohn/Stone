package com.stone.test.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializeTest {

	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		Address address = new Address();
		address.host = "localhost";
		address.port = 8888;
		// serverInfo
		ServerInfo serverInfo = new ServerInfo();
		serverInfo.name = "test";
		serverInfo.address = address;
		writeToFile(serverInfo);
		// read from file
		ServerInfo readInfo = readFromFile();
		System.out.println(readInfo.name);
	}

	private static ServerInfo readFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(new File("Test.txt")));
			return (ServerInfo) in.readObject();
		} finally {
			in.close();
		}
	}

	private static void writeToFile(ServerInfo serverInfo) throws FileNotFoundException, IOException {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("Test.txt")));
		out.writeObject(serverInfo);
		out.close();
	}

	static class ServerInfo implements Serializable {
		private static final long serialVersionUID = 1307428493488791684L;
		String name;
		Address address;
	}

	static class Address implements Serializable {
		private static final long serialVersionUID = -7136029796732918177L;
		String host;
		int port;
	}
}
