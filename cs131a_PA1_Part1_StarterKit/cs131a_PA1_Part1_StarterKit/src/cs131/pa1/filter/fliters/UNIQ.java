package cs131.pa1.filter.fliters;
import cs131.pa1.filter.sequential.SequentialFilter;
import java.util.*;
public class UNIQ extends SequentialFilter
{   HashSet<String> uniq;

	@Override
	protected String processLine(String line)
	{
		if(!uniq.contains(line))
		{
			uniq.add(line);
			return line;
		}
		return null;
	}
}
