import java.util.*;

public class DirectoryServer {

	public static void main(String[] args) {

		newDirectory d = new newDirectory(args[0]);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome to the Directory Server");
		System.out.println("Command: find LastName | delete UCID  | add ");
		System.out.println("To end, enter ^Z");

		while (scanner.hasNext()) {
			String command = scanner.next();
			if (command.equalsIgnoreCase("find"))

			{
				String lastname = scanner.next();

				if (d.find(lastname))
					System.out.print("");

				else
					System.out.println(lastname + " is not in the directory ");
			}

			else if (command.equalsIgnoreCase("delete")) {
				String ucid = scanner.next();

				if (d.delete(ucid))
					System.out.println(ucid + " deleted");

				else
					System.out.println(ucid + " NOT in the directory");
			}

			else if (command.equals("add"))
				d.add();
		}

		d.closeDirectory();
		scanner.close();
	}

}
