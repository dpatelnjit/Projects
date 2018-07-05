import java.io.*; 
import java.net.*;

public class Client { 

    public static void main(String[] args) throws Exception {
    	
        System.out.println("Client is Online!");
        
        Socket cs1 = new Socket("afsaccess1.njit.edu", 11111);
        Socket cs2 = new Socket("afsaccess2.njit.edu", 22222);
        Socket cs3 = new Socket("afsaccess3.njit.edu", 33333);
        
        int[][] routerData0 = {{0,250,0},{1,0,1},{2,1,3},{3,2,7}};
        int[][] routerData1 = new int[4][3];
  	  	int[][] routerData2 = new int[4][3];
  	  	int[][] routerData3 = new int[4][3];
  	  	
	  	DataOutputStream toServer1 = new DataOutputStream(cs1.getOutputStream());
	  	DataOutputStream toServer2 = new DataOutputStream(cs2.getOutputStream());
	  	DataOutputStream toServer3 = new DataOutputStream(cs3.getOutputStream());
  	
        BufferedReader fromServer1 = new BufferedReader(new InputStreamReader(cs1.getInputStream())); 
        BufferedReader fromServer2 = new BufferedReader(new InputStreamReader(cs2.getInputStream()));
        BufferedReader fromServer3 = new BufferedReader(new InputStreamReader(cs3.getInputStream()));
        
        
        System.out.println("Router 0 Data: ");
        System.out.println("Router  |  Interface  |  Cost\n");
        for(int i = 0; i<routerData0.length; i++){
    		for(int j = 0; j<routerData0[i].length;j++){
    			System.out.print(routerData0[i][j]+"            ");
    		}
    		System.out.println();
    	}
        
        System.out.println("Router 1 Data: ");
        System.out.println("Router  |  Interface  |  Cost\n");
        for(int i = 0; i<routerData1.length; i++){
    		for(int j = 0; j<routerData1[i].length;j++){
    			routerData1[i][j]= fromServer1.read();
    			System.out.print(routerData1[i][j]+"            ");
    		}
    		System.out.println();
    	}
        

        int[][] newValue = new int[4][3];
       
        System.out.println("Router 2 Data: ");
        System.out.println("Router  |  Interface  |  Cost\n");
        for(int i = 0; i<routerData2.length; i++){
    		for(int j = 0; j<routerData2[i].length;j++){
    			routerData2[i][j]= fromServer2.read();
    			System.out.print(routerData2[i][j]+"            ");
    		}
    		System.out.println();
    	}
        System.out.println();
        System.out.println("Data after 1st Update: ");
        System.out.println("Router  |  Interface  |  Cost\n");
        for (int i = 0; i <newValue.length; i++){
      	   for (int j = 0; j <newValue[i].length; j++){
 	        	   if(routerData0[i][0] == routerData2[i][0]){
 	        		   if((routerData2[i][1]!=250)||(routerData2[i][1]!=65533)){
 	        			   if(routerData0[i][2]>routerData0[2][2]+routerData2[i][2]){
 	        				   newValue[i][j] = routerData2[i][j];
 	        				   newValue[i][2]= routerData0[2][2]+routerData2[i][2];	        				   
 	        			   }else{
  	        				   newValue[i][j] = routerData0[i][j];
 	        			   }
 	        		   }else if(i==2) {
 	        			   newValue[i][j] = routerData0[i][j];
 	        		   }
 	        		   if (i == 0){
 	        			   newValue[i][j] = routerData0[i][j];
 	        		   }
 	        	   }
 	        	   System.out.print(newValue[i][j]+"            ");
 	           }System.out.println();
         }
         System.out.println();
         for(int i = 0; i < newValue.length; i++){
             for (int j = 0; j< newValue[i].length; j++){
          	   toServer1.writeByte(newValue[i][j]);
          	   toServer2.writeByte(newValue[i][j]);
          	   toServer3.writeByte(newValue[i][j]);
             }
         }
        
         System.out.println("Router 3 Data: ");
         System.out.println("Router  |  Interface  |  Cost\n");
         for(int i = 0; i<routerData3.length; i++){
     		for(int j = 0; j<routerData3[i].length;j++){
     			routerData3[i][j]= fromServer3.read();
     			System.out.print(routerData3[i][j]+"            ");
     		}
     		System.out.println();
     	}
         System.out.println();
         System.out.println("Data after 2nd Update: ");
         System.out.println("Router  |  Interface  |  Cost\n");
         for (int i = 0; i <newValue.length; i++){
       	   for (int j = 0; j <newValue[i].length; j++){
  	        	   if(routerData0[i][0] == routerData2[i][0] && routerData1[i][0] == routerData1[i][0]){
  	        		   if((routerData2[i][1]!=250)||(routerData2[i][1]!=65533)){
  	        			   if(routerData0[i][2]>routerData0[1][2]+routerData1[2][2]+routerData2[i][2]){
  	        				   newValue[i][j] = routerData2[i][j];
  	        				   newValue[i][2]= routerData0[1][2]+routerData1[2][2]+routerData2[i][2];	        				   
  	        			   }else{
  	        				   newValue[i][j] = routerData0[i][j];
  	        			   }
  	        		   }if(i==2) {
  	        			   newValue[i][j] = routerData0[i][j];
  	        		   }
  	        		   if (i == 0){
  	        			   newValue[i][j] = routerData0[i][j];
  	        		   }
  	        	   }
  	        	   System.out.print(newValue[i][j]+"            ");
  	           }System.out.println();
          }
          System.out.println();
          for(int i = 0; i < newValue.length; i++){
              for (int j = 0; j< newValue[i].length; j++){
           	   toServer1.writeByte(newValue[i][j]);
           	   toServer2.writeByte(newValue[i][j]);
           	   toServer3.writeByte(newValue[i][j]);
              }
          }
        		
		cs1.close();
		cs2.close();
		cs3.close();
		
		System.out.println("Connection Terminated!");
    }
}