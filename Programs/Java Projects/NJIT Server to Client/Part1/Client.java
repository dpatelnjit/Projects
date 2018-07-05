import java.io.*;
import java.net.*;

public class Client {
	
	public static void main(String[] args) throws Exception {
		Client client = new Client();
		client.run();
	}
	
	public void run() throws Exception {
		Socket sock = new Socket("afsconnect2.njit.edu", 50007);
		PrintStream PS = new PrintStream(sock.getOutputStream());
		String sendMessage = "Router 0 (Client) | Initial Known Least Cost: Router 0 -> "
				+ "Router 1: 1 | "
				+ "Router 2: 3 | "
				+ "Router 3: 7 ";
		System.out.println(sendMessage);
		PS.println("Message from Client: Client IP: " + sock.getInetAddress() + " Port: " + sock.getPort() + " " + sendMessage);
		
		InputStreamReader IR = new InputStreamReader(sock.getInputStream());
		BufferedReader BR = new BufferedReader(IR);
		
		String serverMessage = BR.readLine();
		System.out.println(serverMessage);
	}
}
