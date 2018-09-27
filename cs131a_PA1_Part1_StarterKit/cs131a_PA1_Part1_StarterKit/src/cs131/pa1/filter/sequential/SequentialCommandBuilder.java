package cs131.pa1.filter.sequential;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import cs131.pa1.filter.fliters.*;
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
		String[] s = subCommand.split(" ");
//		System.out.println(Arrays.toString(s));
		SequentialFilter filter = null;
		switch (s[0])
		{
			case "cat":
				filter = new CAT(s[1]);
				break;
			case "grep":
				filter = new GREP(s[1]);
				break;
			case "uniq":
				filter = new UNIQ();
				break;
			case ">":
				filter = new FILE(s[1]);
				break;
			default:
				System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(s[0]));
				return null;
		}
		return filter;
	}

	private static boolean linkFilters(List<SequentialFilter> filters)
	{
		return false;
	}
}
