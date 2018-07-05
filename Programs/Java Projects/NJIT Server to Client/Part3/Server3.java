import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server3 {
	
	public static void main(String[] args)throws Exception {
		
	  int[][] routerData3 = {{0,0,7},{1,250,0},{2,2,2},{3,250,0}};
      int[][] newValue = new int[4][3];
      
      System.out.println("Server 3 is Online!");
      ServerSocket ss = new ServerSocket(33333); 
      
      while(true) { 
    	  Socket cs = ss.accept(); 
    	  System.out.println("Connection Established!");
    	  BufferedReader fromClient = new BufferedReader(new InputStreamReader(cs.getInputStream())); 
           DataOutputStream  toClient = new DataOutputStream(cs.getOutputStream());
           
           System.out.println("Router 3 Data: ");
           System.out.println("Router  |  Interface  |  Cost\n");
           for(int i = 0; i < routerData3.length; i++){
	           for (int j = 0; j< routerData3[i].length; j++){
	        	   toClient.writeByte(routerData3[i][j]);
	        	   System.out.print(routerData3[i][j]+"            ");
	           }
	           System.out.println();
	       }
                    
           System.out.println();
           System.out.println("Data after 1st Update: ");
           System.out.println("Router  |  Interface  |  Cost\n");
           for(int i = 0; i < newValue.length; i++){
	           for (int j = 0; j< newValue[i].length; j++){
	        	   newValue[i][j] = fromClient.read();
	        	   System.out.print(newValue[i][j]+"            ");
	           }
	           System.out.println();
	       }
           
           System.out.println();
           System.out.println("Data after 2nd Update: ");
           System.out.println("Router  |  Interface  |  Cost\n");
           for(int i = 0; i < newValue.length; i++){
	           for (int j = 0; j< newValue[i].length; j++){
	        	   newValue[i][j] = fromClient.read();
	        	   System.out.print(newValue[i][j]+"            ");
	           }
	           System.out.println();
	       }
      	}
	}
}