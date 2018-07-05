
/***
 * Dhruv Patel - dyp6
 * Project 3
 * CS 341
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class p3_16f_dyp6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in);
		String problem = null; //store inputed encoded problem
		int state = 1; //initial parser state

		System.out.println("Press Y or N to check an encoded problem");
		while (!scan.nextLine().equalsIgnoreCase("n")) {
			System.out.println("Please enter the encoded problem instance <B>");

			problem = scan.nextLine(); // get inputed problem
			state = 1; //always start parser at state 0

			/* INITIALIZE Q, Σ, δ, q0, F */
			
			String[] splitqStates = null;
			String[] splitSymbols = null;
			String[] splitTransitions = null;
			String[] splitacceptStates = null;

			String qstates = "";
			String symbols = "";
			String transitions = "";
			String q0 = "";
			String acceptStates = "";

			String qStatesToString = "";
			String symbolsToString = "";
			String acceptStatesToString = "";
			
			List<String> setS = new ArrayList<String>(); //store states reachable from q0
			List<String> transitionList = new ArrayList<String>(); //store all δ states per Q state
			
			setS.add("q0"); //add initial q0 state in set S
			
			System.out.println(problem + "\n");
			for (char c : problem.toCharArray()) { // iterate through each character within the problem

				switch (state) {
				case (1): {
					if (c == '{')
						state = 2;
					break;
				}
				case (2): {
					if (c == '}') {
						splitqStates = qstates.split(",");
						qStatesToString = Arrays.toString(splitqStates).replace('[', '{').replace(']', '}');
						System.out.println("Q: " + qStatesToString + "\n"); //print properly formatted Q states
						state = 3;
					} else
						qstates += c; //store all Q states
					break;
				}
				case (3): {
					if (c == ',')
						state = 4;
					break;
				}
				case (4): {
					if (c == '{')
						state = 5;
					break;
				}
				case (5): {
					if (c == '}') {
						splitSymbols = symbols.split(",");
						symbolsToString = Arrays.toString(splitSymbols).replace('[', '{').replace(']', '}');
						System.out.println("Σ: " + symbolsToString + "\n"); //print properly formatted Σ
						state = 6;
					} else
						symbols += c; //store all symbols
					break;
				}
				case (6): {
					if (c == ',')
						state = 7;
					break;
				}
				case (7): {
					if (c == '{')
						state = 8;
					break;
				}
				case (8): {
					if (c == '}') {
						splitTransitions = transitions.split(",");
						String transitionsToString = symbolsToString.replace("[", "").replace("]", "").replace(",", "   ");
						System.out.print("δ: \nState | " + transitionsToString.replace("{", "").replace("}", "") + "\n"); //δ header

						String startState = "";
						String transitionState = ""; //store transitions for checking S sets
						for (int i = 0; i < splitTransitions.length; i++) {
							String[] transition = splitTransitions[i].replace("/", "").split(":");
							if (!startState.equals(transition[0])) { //print new line only if it's new Q state transition - transition[0]
								if(transitionState != ""){
									transitionList.add(transitionState.substring(0, transitionState.length()-1)); //add current transition for each state
									transitionState = ""; //look for more transitions per Q state
								}
								System.out.print("  " + transition[0] + "  | ");
								System.out.print(transition[2] + "  ");
								transitionState +=  (transition[0] + " -> " + transition[1] + " -> " + transition[2] + "|");
							} else {
								System.out.print(transition[2] + "  " + "\n");
								transitionState += (transition[0] + " -> " + transition[1] + " -> " + transition[2] + "|");
							}
							startState = transition[0];
						}
						transitionList.add(transitionState.substring(0, transitionState.length()-1)); //add the last transition
						System.out.println();
						state = 9;
					} else
						transitions += c;
				}
					break;
				case (9): {
					if (c == ',')
						state = 10;
					break;
				}
				case (10): {
					if (c == ',') {
						System.out.println("q0: " + q0 + "\n"); //print q0
						state = 11;
					} else
						q0 += c;
					break;
				}
				case (11): {
					if (c == '{')
						state = 12;
					break;
				}
				case (12): {
					if (c == '}') {
						splitacceptStates = acceptStates.split(",");
						acceptStatesToString = Arrays.toString(splitacceptStates).replace('[', '{').replace(']', '}');
						System.out.println("F: " + acceptStatesToString + "\n");  //print properly formatted F:
					} else
						acceptStates += c; //add all Accept States together
					break;
				}				
				}
			}
			System.out.println("S set: " + setS.toString().replace('[', '{').replace(']', '}'));

			for (int i = 0; i < transitionList.size(); i++) {
				String[] transitionSplit = transitionList.get(i).split("\\|"); //get each transition per Q state
				for(int j = 0; j < transitionSplit.length; j++){
					String[] transition = transitionSplit[j].split(" -> "); //get transition information split up 
					if(!setS.contains(transition[2])){
						setS.add(transition[2]); //only add state to set S if it doesn't contain it
					}
				}
				System.out.println("δ Transition: " + transitionList.get(i).replace("|", " , ") + " | S set: " + setS.toString().replace('[', '{').replace(']', '}'));
			}
						
			System.out.println("\nS set: " + setS.toString().replace('[', '{').replace(']', '}'));
			System.out.println("F set: " + acceptStatesToString);
			
			boolean validProblem = true;
			for(int i = 0; i < splitacceptStates.length; i++){
				if(setS.contains(splitacceptStates[0])){  //set S has some element intersecting with set F
					validProblem = false;
					break;
				}
			}
			
			if(validProblem){
				System.out.println("\nS ∩ F = ∅");
				System.out.println("accept"); //accepted encoded problem since none of the elements from sets S and F intersect
			} else {
				System.out.println("\nS ∩ F != ∅");
				System.out.println("reject");
			}
			
			System.out.println("Press Y or N to check an encoded problem");

		}
	}
}
