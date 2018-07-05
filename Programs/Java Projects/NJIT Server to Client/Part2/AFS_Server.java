import java.io.*; 
import java.net.*; 

public class AFS_Server {
	
	public static void main(String[] args) throws Exception { 
	  
	  BufferedReader clientData;
	  DataOutputStream serverData; 
	  int[][] routerData_0 = new int[4][3];; 
	  int[][] routerData_1 = {{0,2,1},{1,5,0},{2,0,1},{3,5,0}};
      int[][] ab = new int[4][3];

      ServerSocket server_socket = new ServerSocket(3333); 
      System.out.print("Server is Online\n"); 
      
      while(true) { 
    	  Socket client_socket = server_socket.accept(); 
    	  
    	  clientData = new BufferedReader(new InputStreamReader(client_socket.getInputStream())); 
          serverData = new DataOutputStream(client_socket.getOutputStream());
          
    	  System.out.println("Connection Established");	             
           
          System.out.println("Received the Router Data");
          System.out.println("Router  |  Interface  |  Cost\n");
                      
          for(int x = 0; x < routerData_0.length; x++){
        	  
        	  for (int y = 0; y< routerData_0[x].length; y++){
        		  routerData_0[x][y] = clientData.read();
        		  System.out.print(routerData_0[x][y] + "            ");
	          }
        	  
        	  System.out.print("\n");
	       }
           
           System.out.print("\n");           
           
           System.out.print("Router 1 Data\n");
           System.out.println("Router  |  Interface  |  Cost\n");
           for(int x = 0; x < routerData_1.length; x++){
        	   
	           for (int y = 0; y < routerData_1[x].length; y++){
	        	   System.out.print(routerData_1[x][y] + "            ");
	           }
	           System.out.print("\n");
	           
	       }

           System.out.print("\n");
           
           System.out.print("New Router 0 Data: \n");
           System.out.println("Router  |  Interface  |  Cost\n");
           
           for (int x = 0; x < ab.length; x++){
        	   
        	   for (int y = 0; y < ab[x].length; y++){
        		   
	        	   if(routerData_0[x][0] == routerData_1[x][0]){	
	        		   
	        		   if(routerData_1[x][1] != 5){	        			   
	        			   if(routerData_0[x][2] < routerData_0[x][2]+routerData_1[x][2]){	        				   
	        				   ab[x][y] = routerData_1[x][y];
	        				   ab[x][2] = routerData_0[1][2]+routerData_1[x][2];	        		        				   
	        			   }	        			   
	        		   }
	        		   
	        		   else if(routerData_1[x][1] == 5) {
	        			   ab[x][y] = routerData_0[x][y];
	        		   }
	        		   
	        		   if (x == 0){
	        			   ab[x][y] = routerData_0[x][y];
	        		   }	        		   
	        	   }
	        	   System.out.print(ab[x][y] + "            ");
	           } 
        	   System.out.print("\n");
           }
           
           for (int x = 0; x < ab.length;x++){
        	   
        	   for (int y = 0; y < ab[x].length; y++){
        		   serverData.writeByte(ab[x][y]);
        	   }
           }
      	}
    }
}