import java.io.*;
import java.net.*;

public class Server {
	
	public static void main(String[] args) throws Exception {
		Server server = new Server();
		server.run();
	}
	
	public void run() throws Exception {
		ServerSocket serverSock = new ServerSocket(50007);
		Socket sock = serverSock.accept();

		InputStreamReader IR = new InputStreamReader(sock.getInputStream());
		BufferedReader BR = new BufferedReader(IR);
		
		String clientMessage = BR.readLine();
		System.out.println(clientMessage);
		
		String sendMessage = "Router 1 (Server) | Initial Known Least Cost: Router 1 ->"
				+ "Router 0: 1 | "
				+ "Router 2: 1 | "
				+ "Router 3: 3";
		
		PrintStream PS = new PrintStream(sock.getOutputStream());
		PS.println("Message from Server: " + "Server IP: " + sock.getInetAddress() + " Port: " + sock.getLocalPort() + " " + sendMessage);
	}
}
