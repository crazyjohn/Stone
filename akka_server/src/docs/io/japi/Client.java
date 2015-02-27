package docs.io.japi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	Socket socket;
	BufferedReader in;
	PrintWriter out;

	public Client() {
		try {
			socket = new Socket("192.168.0.118", 8000);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
//			BufferedReader line = new BufferedReader(new InputStreamReader(System.in));
//			out.println(line.readLine());
//			line.close();
//			for(int i = 0; i < 100; i++)
			String msg = "hello world2";
				out.println("hello world2");
			Thread.sleep(5000);
			String str = in.readLine();
			if(str != null)
			{
				System.out.println("client receive "+str);
				str = in.readLine();
			}
//			out.close();
//			in.close();
//			socket.close();
			Thread.sleep(100000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Client();
	}
}
