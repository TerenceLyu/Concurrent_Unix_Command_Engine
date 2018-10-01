package cs131.pa1.filter.sequential;
import java.util.*;
import cs131.pa1.filter.Message;

public class SequentialREPL {
	static String currentWorkingDirectory;
	static boolean error = false;
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print(Message.WELCOME);
		currentWorkingDirectory = System.getProperty("user.dir");
		
		boolean exit = false;
		while (!exit) {
			System.out.print(Message.NEWCOMMAND);
			String command = input.nextLine();
			if (!command.equals("exit")) {
				LinkedList listOfCommand = (LinkedList) SequentialCommandBuilder.createFiltersFromCommand(command);
				if (listOfCommand!=null) {
					Iterator iterator = listOfCommand.listIterator();
					SequentialFilter curr = (SequentialFilter) iterator.next();
					SequentialFilter prev;
					try {
						curr.process();
					}catch (Exception e) {
						error = true;
						System.out.print(Message.REQUIRES_INPUT.with_parameter(curr.toString()));
					}
					while (iterator.hasNext() && !error) {
						prev = curr;
						if (prev.toString().contains("cd")||prev.toString().contains(">")) {
							System.out.print(Message.CANNOT_HAVE_OUTPUT.with_parameter(prev.toString()));
							error = true;
						}else {
							curr = (SequentialFilter) iterator.next();
							if (curr.toString().contains("cd")||curr.toString().contains("pwd")||curr.toString().contains("ls")||curr.toString().contains("cat")) {
								System.out.print(Message.CANNOT_HAVE_INPUT.with_parameter(curr.toString()));
								error = true;
							}
							curr.setPrevFilter(prev);
							curr.process();
						}
						
					}
					while (!curr.output.isEmpty()&&!error) {
						System.out.println(curr.output.poll());
					}
				}
				
			}else {
				exit = true;
			}
			error = false;
		}
		System.out.print(Message.GOODBYE);
	}

}
