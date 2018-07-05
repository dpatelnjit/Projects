
/* DHRUV PATEL    CS435    8596    mp */

import java.io.File;
import java.text.DecimalFormat;

public class pagerank8596 {

	public static void main(String[] args) {
		int iterations = 0; 
		int initialvalue = 0; //just to assign variables, will override when reading arguments
		String fileName = null;
		
		if(args.length!=3){
			System.out.println("Pass 3 arguments!");
			System.exit(0);
		}
		try{
			iterations = Integer.parseInt(args[0]);
			initialvalue = Integer.parseInt(args[1]);
			fileName = args[2];
		}catch(NumberFormatException nfe){
			System.out.println("Arguments are not in proper order or are invalid!");
			System.exit(0);
		}
		
		Graph8596 graph = Graph8596.createGraph8596(new File(fileName),initialvalue);
		graph.display8596(iterations);
	}
}