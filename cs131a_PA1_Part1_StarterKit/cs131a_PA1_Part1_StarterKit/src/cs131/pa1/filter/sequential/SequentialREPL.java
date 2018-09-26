package cs131.pa1.filter.sequential;
import java.util.*;
import cs131.pa1.filter.sequential.SequentialCommandBuilder;
import cs131.pa1.filter.Message;
public class SequentialREPL
{

	static String currentWorkingDirectory;

	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print(Message.WELCOME);
		boolean exit = false;
		while (!exit)
		{
			System.out.print(Message.NEWCOMMAND);
			String command = input.next();
			if (!command.equals("exit"))
			{
				LinkedList listOfCommand = (LinkedList) SequentialCommandBuilder.createFiltersFromCommand(command);
				Iterator iterator = listOfCommand.listIterator();
				SequentialFilter curr = (SequentialFilter) iterator.next();
				SequentialFilter prev;
				curr.process();
				while (iterator.hasNext())
				{
					prev = curr;
					curr = (SequentialFilter) iterator.next();
					curr.setPrevFilter(prev);
					curr.process();
				}
			}else
			{
				exit = true;
			}
		}
		System.out.print(Message.GOODBYE);
	}

}
