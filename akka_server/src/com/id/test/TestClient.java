package com.id.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class TestClient {
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;

	public TestClient() {
		try {
			socket = new Socket("192.168.0.118", 8000);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			String uuid = "hello";
			byte[] data = uuid.getBytes();
			dos.writeInt(data.length);
			dos.write(data);
//			dos.flush();
			try
			{
				Thread.sleep(5000);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			System.out.println("aaaa");
//			int len = dis.readInt();
//			byte[] receive = new byte[len];
//			dis.read(receive);
//			System.out.println(new String(receive));
//			dos.writeInt(data.length);
//			dos.write(data);
			dos.flush();
			dos.close();
			dis.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		new TestClient();
	}
}
