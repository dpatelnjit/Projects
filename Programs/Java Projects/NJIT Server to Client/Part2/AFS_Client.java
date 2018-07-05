import java.io.*; 
import java.net.*; 

public class AFS_Client { 
	
    public static void main(String[] args) throws Exception {
    	
        System.out.print("Client is Online\n");
        Socket client_socket = new Socket("afsaccess2.njit.edu", 3333);
        
        DataOutputStream clientData = new DataOutputStream(client_socket.getOutputStream()); 
        BufferedReader serverData = new BufferedReader(new InputStreamReader(client_socket.getInputStream())); 

        int[][] routerData_0= {{0,5,0},{1,0,1},{2,1,3},{3,2,7}};
        int[][] ab = new int[4][3];
        
        for(int i = 0; i < routerData_0.length; i++){        	
    		for(int j = 0; j <routerData_0[i].length; j++){
    			clientData.writeByte(routerData_0[i][j]);
    		}
        }
		System.out.print("Server Message: \n");
		System.out.print("New Router Data\n");
		System.out.println("Router  |  Interface  |  Cost\n");

		for(int i = 0; i < ab.length; i++){
			for (int j = 0; j < ab[i].length; j++){
				ab[i][j] = serverData.read();
        	    System.out.print(ab[i][j] + "            ");
			}
	        System.out.print("\n");
	    }
		
		client_socket.close();
		System.out.print("Connection is now closed\n");
		             
    }
}