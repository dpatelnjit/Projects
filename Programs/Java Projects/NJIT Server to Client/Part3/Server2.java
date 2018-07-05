import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
	
	public static void main(String[] args)throws Exception {
		
	  int[][] routerData2 = {{0,2,3},{1,0,1},{2,250,0},{3,2,2}};
      int[][] newValue = new int[4][3];
      
      System.out.println("Server 2 is Online!");
      ServerSocket ss = new ServerSocket(22222); 
      
      while(true) { 
    	  Socket cs = ss.accept(); 
    	  System.out.println("Connection Established!");
    	  BufferedReader fromClient = new BufferedReader(new InputStreamReader(cs.getInputStream())); 
          DataOutputStream  toClient = new DataOutputStream(cs.getOutputStream());
           
           System.out.println("Router 2 Data: ");
           System.out.println("Router  |  Interface  |  Cost\n");
           for(int i = 0; i < routerData2.length; i++){
	           for (int j = 0; j< routerData2[i].length; j++){
	        	   toClient.writeByte(routerData2[i][j]);
	        	   System.out.print(routerData2[i][j]+"            ");
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