package cs131.pa1.filter.sequential;

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
	
	@Override
	public String toString()
	{
		return "grep " + word;
	}
}