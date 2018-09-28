package cs131.pa1.filter.sequential;
import java.util.*;

public class UNIQ extends SequentialFilter
{
	HashSet<String> uniq;
	
	public UNIQ()
	{
		this.output = new LinkedList<>();
		this.uniq = new HashSet<>();
	}
	
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
