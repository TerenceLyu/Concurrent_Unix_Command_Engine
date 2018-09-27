package cs131.pa1.filter.fliters;
import cs131.pa1.filter.sequential.SequentialFilter;

import java.util.LinkedList;

public class GREP extends SequentialFilter
{
	String word;
	
	
	public GREP(String word)
	{
		this.word = word;
		this.output = new LinkedList<>();
	}
	
	
	@Override
	protected String processLine(String line)
	{
		if(line.contains(word))
		{
			return line;
		}
		return null;
	}
}