
/***
 * Dhruv Patel - dyp6
 * Project 2
 * CS 341
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class p2_16f_dyp6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in);
		String string = null; 

		List<Character> stack = new ArrayList<Character>();  //keep track of pop/push within the PDA

		int state = 1;  //PDA starts at state 1

		System.out.println("Press Y or N to check string");

		while (!scan.nextLine().equalsIgnoreCase("n")) {
			System.out.println("Please enter a string to validate");

			string = scan.nextLine(); // get input string

			state = 1; // initial starting stage = 1
			boolean invalidChar = false; // unrecognized character that doesn't fit DFA
			stack.clear();  // new stack for each String
			for (char c : string.toCharArray()) { // iterate through each character within the string

				char pop = 'ϵ'; 
				char push = 'ϵ'; //by default don't pop/push anything

				if (invalidChar) {
					System.out.println("String: " + string + " | Invalid symbol found - PDA Crashed");
					break;
				}

				int oldState = state; // remember the state transitions per character | q1-->q2

				switch (state) {
				case (1): {
					if (c == '$') {
						state = 2;
						stack.add(c); //push '$' to the stack
						push = c;
					} else
						invalidChar = true;
				}
					break;
				case (2): {
					if (c == '(') {
						state = 6;
						stack.add(c); //push '(' to the stack
						push = c;
					} else if (Character.isLetter(c) || c == '_') {
						state = 3;
					} else if (Character.isDigit(c)) {
						state = 4;
					} else
						invalidChar = true;
				}
					break;
				case (3): {
					if (Character.isLetter(c) || Character.isDigit(c) || c == '_') {
						state = 3;
					} else if (c == '+' || c == '-' || c == '*' || c == '/') {
						state = 5;
					} else if (c == ')') {
						if (stack.contains('(')){
							state = 7;
							stack.remove(Character.valueOf('(')); //pop '(' from the stack when we find ')' --> only if '(' is inside the stack
							pop = '(';
						} else {
							invalidChar = true;
						}

					} else if (c == '$') {
						if (stack.contains('$')){
							state = 8; //accepted state
							stack.remove(Character.valueOf(c)); //pop '$' from the stack
							pop = c;
						} else {
							invalidChar = true;
						}
					} else
						invalidChar = true;
				}
					break;
				case (4): {
					if (Character.isDigit(c)) {
						state = 4;
					} else if (c == '+' || c == '-' || c == '*' || c == '/') {
						state = 5;
					} else if (c == ')') {
						if (stack.contains('(')){
							state = 7;
							stack.remove(Character.valueOf('(')); //pop '(' from the stack when we find ')' --> only if '(' is inside the stack
							pop = '(';
						} else {
							invalidChar = true;
						}
					} else if (c == '$') {
						if (stack.contains('$')){
							state = 8; //accepted state
							stack.remove(Character.valueOf(c)); //pop '$' from the stack
							pop = c;
						} else {
							invalidChar = true;
						}
					} else
						invalidChar = true;
				}
					break;
				case (5): {
					if (Character.isLetter(c) || c == '_') {
						state = 3;
					} else if (Character.isDigit(c)) {
						state = 4;
					} else if (c == '(') {
						state = 6;
						stack.add(c);  //push '(' to the stack
						push = c;
					} else
						invalidChar = true;
				}
					break;
				case (6): {
					if (c == '(') {
						state = 6;
						stack.add(c); //push '(' to the stack
						push = c;
					} else if (Character.isLetter(c) || c == '_') {
						state = 3;
					} else if (Character.isDigit(c)) {
						state = 4;
					} else
						invalidChar = true;
				}
					break;
				case (7): {
					if (c == ')') {
						if (stack.contains('(')){
							state = 7;
							stack.remove(Character.valueOf('('));  //pop '(' from the stack when we find ')' --> only if '(' is inside the stack
							pop = '(';
						} else {
							invalidChar = true;
						}
					} else if (c == '+' || c == '-' || c == '*' || c == '/') {
						state = 5;
					} else if (c == '$') {
						if (stack.contains('$')){
							state = 8; //accepted state
							stack.remove(Character.valueOf(c)); //pop '$' from the stack
							pop = c;
						} else {
							invalidChar = true;
						}
					} else
						invalidChar = true;
				}
					break;

				}
				System.out.println("String: " + string + " | " + c + ", " + pop + " -> " + push + " | State: q" + oldState + "-->q" + state);

			}

			if (state == 8 && stack.isEmpty()) { //string ended up in the only accepted state (q8) and stack is fully empty
				System.out.println("** String: " + string + " | Accepted String **\n");
			} else {
				System.out.println("** String: " + string + " | Rejected String **\n");
			}
			
			System.out.println("Press Y or N to check string");

		}

	}
}
