package cs131.pa1.filter.sequential;


import java.util.*;
import cs131.pa1.filter.Message;

public class SequentialCommandBuilder
{

	public static List<SequentialFilter> createFiltersFromCommand(String command)
	{
		List<SequentialFilter> list = new LinkedList<>();
		for (String s: command.split("\\|"))
		{
			SequentialFilter sf = constructFilterFromSubCommand(s);
			if (sf!=null)
			{
				list.add(sf);
			}else
			{
				return null;
			}
		}
		return list;
	}

	private static SequentialFilter determineFinalFilter(String command)
	{
		return null;
	}

	private static String adjustCommandToRemoveFinalFilter(String command)
	{
		return null;
	}

	private static SequentialFilter constructFilterFromSubCommand(String subCommand)
	{
		String[] s = subCommand.trim().split(" ");
//		System.out.println(Arrays.toString(s));
		SequentialFilter filter = null;
		switch (s[0])
		{
			case "cat":
				try
				{
					filter = new CAT(s[1]);
				}catch (Exception e)
				{
					SequentialREPL.error = true;
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(s[0]));
				}
				break;
			case "grep":
				try
				{
					filter = new GREP(s[1]);
				}catch (Exception e)
				{
					SequentialREPL.error = true;
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(s[0]));
				}
				break;
			case "uniq":
				filter = new UNIQ();
				break;
			case ">":
				try
				{
					filter = new WRITE(s[1]);
				}catch (Exception e)
				{
					SequentialREPL.error = true;
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(s[0]));
				}
				break;
			case "wc":
				filter = new WC();
				break;
			case "pwd":
				filter = new PWD();
				break;
			case "ls":
				filter = new LS();
				break;
			case "cd":
				filter = new CD(s[1]);
				break;
			default:
				SequentialREPL.error = true;
				System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand.trim()));
				return null;
		}
		return filter;
	}

	private static boolean linkFilters(List<SequentialFilter> filters)
	{
		return false;
	}
}
