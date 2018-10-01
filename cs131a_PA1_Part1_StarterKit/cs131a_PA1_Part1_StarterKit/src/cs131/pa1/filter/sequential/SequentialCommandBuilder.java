package cs131.pa1.filter.sequential;


import java.nio.file.Path;
import java.util.*;
import cs131.pa1.filter.Message;

public class SequentialCommandBuilder
{

	public static List<SequentialFilter> createFiltersFromCommand(String command)
	{
		List<SequentialFilter> list = new LinkedList<>();
//		String[] commands = command.split(" ");
//		String subCommand = "";
//		String param = "";
//		Boolean hasParam = false;
//		for (String s: commands)
//		{
//
//			if (s.equals("\\|"))
//			{
//				if (!build(list, subCommand + " " + param))
//				{
//					return null;
//				}
//				hasParam = false;
//			}
//			if (s.equals(">"))
//			{
//				if (!subCommand.equals(""))
//				{
//					if (!build(list, subCommand + " " + param))
//					{
//						return null;
//					}
//				}
//				subCommand = s;
//				hasParam = true;
//			}
//
//			if (!hasParam)
//			{
//				subCommand = s;
//				hasParam = true;
//			}else
//			{
//				param += s + " ";
//			}
//		}
//		if (!build(list, subCommand + " " + param))
//		{
//			return null;
//		}
		
		
		for (String s: command.split("\\|"))
		{
			if (s.contains(">"))
			{
				String firstCommand = s.substring(0, s.indexOf(">"));
				
				if (!firstCommand.equals(""))
				{
					if (!build(list, firstCommand))
					{
						return null;
					}
				}else
				{
					System.out.print(Message.REQUIRES_INPUT.with_parameter(s.substring(s.indexOf(">"))));
					SequentialREPL.error = true;
					return null;
				}
				
				String temp = s.substring(s.indexOf(">"));
				String[] subcommands = s.substring(s.indexOf(">")).replaceFirst(">", "").split(">");
				for (int i = 0; i < subcommands.length; i++)
				{
					build(list, ">" + subcommands[i]);

				}

			}else
			{
				if (!build(list, s))
				{
					return null;
				}
			}


		}
		return list;
	}
	private static boolean build(List<SequentialFilter> l, String command)
	{
		SequentialFilter sf = constructFilterFromSubCommand(command);
		if (sf!=null)
		{
			l.add(sf);
		}else
		{
			return false;
		}
		return true;
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
		String param = "";
		subCommand = subCommand.trim();
		if (subCommand.contains(" "))
		{
			param = subCommand.substring(subCommand.indexOf(" ")+1);
			subCommand = subCommand.substring(0, subCommand.indexOf(" "));
		}
//		System.out.println(subCommand);
//		System.out.println(param);
		SequentialFilter filter = null;
		switch (subCommand)
		{
			case "cat":
				if (!param.equals(""))
				{
					filter = new CAT(param);
				}else
				{
					SequentialREPL.error = true;
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
				}
//				try
//				{
//					filter = new CAT(param);
//				}catch (Exception e)
//				{
//					SequentialREPL.error = true;
//					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
//				}
				break;
			case "grep":
				if (!param.equals(""))
				{
					filter = new GREP(param);
				}else
				{
					SequentialREPL.error = true;
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
				}
//				try
//				{
//					filter = new GREP(param);
//				}catch (Exception e)
//				{
//					SequentialREPL.error = true;
//					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
//				}
				break;
			case "uniq":
				filter = new UNIQ();
				break;
			case ">":
				if (!param.equals(""))
				{
					filter = new WRITE(param);
				}else
				{
					SequentialREPL.error = true;
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
				}
//				try
//				{
//					filter = new WRITE(param);
//				}catch (Exception e)
//				{
//					SequentialREPL.error = true;
//					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
//				}
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
				if (!param.equals(""))
				{
					filter = new CD(param);
				}else
				{
					SequentialREPL.error = true;
					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
				}
//				try
//				{
//					filter = new CD(param);
//				}catch (Exception e)
//				{
//					SequentialREPL.error = true;
//					System.out.print(Message.REQUIRES_PARAMETER.with_parameter(subCommand));
//				}
				break;
			default:
				SequentialREPL.error = true;
				System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(subCommand + " " + param));
				return null;
		}
		return filter;
	}

	private static boolean linkFilters(List<SequentialFilter> filters)
	{
		return false;
	}
}
