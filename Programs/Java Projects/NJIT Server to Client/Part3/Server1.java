import java.io.*; 
import java.net.*; 

public class Server1 {
	
	public static void main(String[] args) throws Exception {
		
	  int[][] routerData1 = {{0,2,1},{1,250,0},{2,0,1},{3,250,0}};
      int[][] newValue = new int[4][3];
      
      System.out.println("Server 1 is Online!");
      ServerSocket ss = new ServerSocket(11111); 
      
      while(true) { 
    	  Socket cs = ss.accept(); 
    	  System.out.println("Connection Established!");
    	  BufferedReader fromClient = new BufferedReader(new InputStreamReader(cs.getInputStream())); 
          DataOutputStream  toClient = new DataOutputStream(cs.getOutputStream());
       
           System.out.println();
           System.out.println("Router 1 Data: ");
           System.out.println("Router  |  Interface  |  Cost\n");
           for(int i = 0; i < routerData1.length; i++){
	           for (int j = 0; j< routerData1[i].length; j++){
	        	   toClient.writeByte(routerData1[i][j]);
	        	   System.out.print(routerData1[i][j]+"            ");
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