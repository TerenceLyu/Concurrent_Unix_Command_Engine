package cs131.pa1.filter.sequential;

import java.util.LinkedList;

public class PWD extends SequentialFilter
{
	
	@Override
	public void process()
	{
		if (this.output == null)
		{
			this.output = new LinkedList<>();
		}
		output.add(SequentialREPL.currentWorkingDirectory);
	}
	
	@Override
	protected String processLine(String line)
	{
		return null;
	}
}
