package cs131.pa1.filter.sequential;

import java.util.LinkedList;
import java.util.List;
import cs131.pa1.filter.fliters.*;
public class SequentialCommandBuilder {
	
	public static List<SequentialFilter> createFiltersFromCommand(String command){
		List<SequentialFilter> list = new LinkedList<>();
		for (String s: command.split("\\|"))
		{
			list.add(constructFilterFromSubCommand(s));
		}
		return list;
	}
	
	private static SequentialFilter determineFinalFilter(String command){
		return null;
	}
	
	private static String adjustCommandToRemoveFinalFilter(String command){
		return null;
	}
	
	private static SequentialFilter constructFilterFromSubCommand(String subCommand){
		String[] s = subCommand.split(" ");
		SequentialFilter filter = null;
		switch (s[0])
		{
			case "cat":
				filter = new CAT();
				break;
			case "grep":
				filter = new GREP(s[1]);
				break;
//			case "":
//				break;
//			case "":
//				break;
//
		}
		return filter;
	}

	private static boolean linkFilters(List<SequentialFilter> filters){
		return false;
	}
}
