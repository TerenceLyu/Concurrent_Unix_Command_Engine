package cs131.pa1.filter.sequential;

public class PWD extends SequentialFilter
{
	
	@Override
	public void process()
	{
		output.add(SequentialREPL.currentWorkingDirectory);
	}
	
	@Override
	protected String processLine(String line)
	{
		return null;
	}
}
