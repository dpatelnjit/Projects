/***
 * Dhruv Patel - dyp6
 * Project 1
 * CS 341
 */

import java.util.Scanner;

public class p1_16f_dyp6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in);
		String URL = null;
		boolean validURL = false;
		int state = 1;


		System.out.println("Press y or n to check URL");

		while (!scan.nextLine().equalsIgnoreCase("n")) {
			System.out.println("Please enter a URL to validate");

			URL = scan.nextLine();  //get URL
			validURL = false;
			state = 1;  //initial starting stage = 1 
			boolean invalidChar = false; // unrecognized character that doesn't fit DFA

			for (char c : URL.toCharArray()) { //iterate through each character
				
				if (invalidChar) {
					System.out.println("Word: " + URL + " | Invalid character found - Doesn't match DFA");
					state = 1;
				}
				
				int oldState = state; //remember the state transitions per character | q1->q2 
				
				switch (state) {
				case (1): {
					if (c == 'w') {
						state = 2;
					} else {
						state = 6;
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (2): {
					if (c == 'w') {
						state = 3;
					} else if (c == '.') {
						state = 7;
					} else {
						state = 6;
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (3): {
					if (c == 'w') {
						state = 4;
					} else if (c == '.') {
						state = 7;
					} else {
						state = 6;
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (4): {
					if (c == '.') {
						state = 5;
					} else {
						state = 6;
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (5): {
					if (c == 'c') {
						state = 15; //check for suffix might be a "www.com" type of URL with no prefix; URL itself could be "www.com"
					} else if (c == '.') {
						invalidChar = true;
					} else {
						state = 6; //if can't find 'c' then "www." must be a prefix
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (6): {
					if (c == '.') { //found actual URL -> look for suffix
						state = 7;
					} else {
						state = 6; //find actual URL through iterations until we find a period
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (7): {
					if (c == 'c') { //check suffix (.com, .co.ca, or .ca), if either invalid URL
						state = 8;
					} else {
						invalidChar = true;
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (8): {
					if (c == 'o') {
						state = 9;
					} else if (c == 'a') {
						state = 14; //suffix is ".ca"
					} else {
						invalidChar = true;
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (9): {
					if (c == 'm') { //suffix is ".com"
						state = 10;
					} else if (c == '.') { //check if suffix is ".co.ca"
						state = 11;
					} else {
						invalidChar = true;
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (11): {
					if (c == 'c') {
						state = 12;
					} else {
						invalidChar = true;
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (12): {
					if (c == 'a') {
						state = 13; //suffix is ".co.ca"
					} else {
						invalidChar = true;
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (10): {
					invalidChar = true; // extra character after .com
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (13): {
					invalidChar = true; // extra character after .co.ca
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (14): {
					invalidChar = true; // extra characters after .ca
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (15): {
					if (c == 'a') {
						state = 16;
					} else if (c == 'o') {
						state = 17;
					} else if (c == '.') {
						state = 7; //found 2 periods -> check for suffix e.g. "www.computer." found
					} else {
						state = 6; //URL with prefix "www." -> check actual URL -> check suffix
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;

				case (16): {
					if (Character.isLetter(c)) {
						state = 6; //URL with prefix "www." -> check actual URL -> check suffix
					} else if (c == '.') {
						state = 7; //found 2 periods -> check for suffix e.g. "www.computer." found
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (17): {
					if (c == 'm') {
						state = 18; 
					} else if (c == '.') {
						state = 7; //found 2 periods -> check for suffix e.g. "www.computer." found
					} else {
						state = 6; //URL with prefix "www." -> check actual URL -> check suffix
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				case (18): {
					if (Character.isLetter(c)) {
						state = 6; //URL with prefix "www." -> check actual URL -> check suffix
					} else if (c == '.') {
						state = 7; //found 2 periods -> check for suffix e.g. "www.computer." found
					}
				}
					System.out.println("Word: " + URL + " | Character Processed: " + c + " | State: q" + oldState
							+ "-->q" + state);
					break;
				}
			}

			if ((state == 10 || state == 13 || state == 14 || state == 16 || state == 18) && !invalidChar) { //if state equals any of these, it's valid URL that follows the DFA 
				System.out.println("** Word: " + URL + " | Valid URL **\n");
			} else {
				System.out.println("** Word: " + URL + " | Invalid URL **\n");
			}
			
			System.out.println("Press y or n to check URL");

		}

	}
}
