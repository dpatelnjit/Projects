import java.util.*;

import java.io.*;

public class newDirectory

{

	final int maxDirectorySize = 1024;
	Person persons[] = new Person[maxDirectorySize];
	int directorySize = 0;
	File directoryFile = null;
	Scanner directoryDataIn = new Scanner(System.in);
	Scanner scanner = new Scanner(System.in);

	public newDirectory(String directoryFileName) {

		directoryFile = new File(directoryFileName);

		try

		{
			directoryDataIn = new Scanner(directoryFile);
		}

		catch (FileNotFoundException e)

		{
			System.out.printf("File %s not found, exiting!", directoryFileName);
			System.exit(0);
		}

		while (directoryDataIn.hasNext()) {

			String position = directoryDataIn.next();
			String ID = directoryDataIn.next();
			String first = directoryDataIn.next();
			String last = directoryDataIn.next();

			OUTER: switch (position) {

			case "Freshman":
			case "Sophomore":
			case "Junior":
			case "Senior":

			{
				String major = directoryDataIn.next();
				String email = directoryDataIn.next();
				Student s = new Student(position, ID, first, last, major, email);
				persons[directorySize++] = s;
				break OUTER;
			}

			case "Adjunct":
			case "Professor":

			{
				String dept = directoryDataIn.next();
				String email = directoryDataIn.next();
				String office = directoryDataIn.next();
				Instructor i = new Instructor(position, ID, first, last, email,	office, dept);
				persons[directorySize++] = i;
				break;
			}

			case "Staff":

			{
				String dept = directoryDataIn.next();
				Employee e = new Employee(position, ID, first, last, dept);
				persons[directorySize++] = e;
				break;
			}
			}
		}
	}

	public void data() {
		for (int i = 0; i < directorySize; i++) {
			System.out.println(persons[i]);
		}
	}

	public boolean find(String name) {
		boolean found = false;
		for (int i = 0; i < directorySize; i++) {
			if (persons[i].getLast().equalsIgnoreCase(name)) {
				found = true;
				System.out.println(persons[i].toString());
			}
		}
		return found;
	}

	public void add() {
		boolean add = false;
		String ucid;
		String position, first, last, dept, office, email, major;
		System.out.println("Enter UCID");
		ucid = scanner.nextLine();
		System.out.println(persons[0].getUCID());
		System.out.println(ucid);

		for (int i = 0; i < directorySize; i++) {
			if (!(ucid.equals(persons[i].getUCID()) && directorySize != maxDirectorySize)) {
				add = true;
			} else {
				add = false;
				System.out.println("UCID already in the database");
			}
		}

		if (add) {

			System.out.println("Enter position. Professor, Adjuct, Staff, Senior, Junior, Sophomore, or Freshmen. MUST match case");
			position = scanner.nextLine();
			System.out.println("Enter first name");
			first = scanner.nextLine();
			System.out.println("Enter last name");
			last = scanner.nextLine();
			switch (position)
			{

			case "Adjunct":
			case "Professor":

				System.out.println("Enter dept");
				dept = scanner.nextLine();
				System.out.println("Enter email");
				email = scanner.nextLine();
				System.out.println("Enter office");
				office = scanner.nextLine();
				Instructor i = new Instructor(position, ucid, first, last, email, office, dept);
				persons[directorySize++] = i;
				System.out.println("Added successfully " + i.toString());
				break;

			case "Staff":

				System.out.println("Enter dept");
				dept = scanner.nextLine();
				Employee e = new Employee(position, ucid, first, last, dept);
				persons[directorySize++] = e;
				System.out.println("Added sucessfully " + e.toString());
				break;

			case "Freshmen":
			case "Sophomore":
			case "Junior":
			case "Senior":

				System.out.println("Enter major");
				major = scanner.nextLine();
				System.out.println("Enter email");
				email = scanner.nextLine();
				Student s = new Student(position, ucid, first, last, major,	email);
				persons[directorySize++] = s;
				System.out.println("Added sucessfully " + s.toString());
				break;
			}
		}
	}

	public boolean delete(String number) {
		boolean delete = false;
		String ucid = number;
		for (int i = 0; i < directorySize; i++) {
			if (ucid.equals(persons[i].getUCID())) {
				for(int j = i; j < directorySize; j++)
				{
					persons[j] = persons[j+1];
				}
				delete = true;
				directorySize--;
				break;
			}
		}
		return delete;
	}

	public void closeDirectory() {
		directoryDataIn.close();
		PrintStream directoryDataOut = null;
		try {
			directoryDataOut = new PrintStream(directoryFile);
		} catch (FileNotFoundException e) {
			System.out.printf("File %s not found, exiting!", directoryFile);
			System.exit(0);
		}

		for (int i = 0; i < directorySize; i++) {
			directoryDataOut.println(persons[i]);
		}
		directoryDataOut.close();
	}
}