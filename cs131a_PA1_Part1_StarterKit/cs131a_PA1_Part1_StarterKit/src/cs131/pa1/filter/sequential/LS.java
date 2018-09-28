package cs131.pa1.filter.sequential;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
public class LS extends SequentialFilter
{
	
	@Override
	public void process()
	{
		
		File f = new File(SequentialREPL.currentWorkingDirectory);
		this.output = new LinkedList<String>(Arrays.asList(f.list()));
	}
	
	@Override
	protected String processLine(String line)
	{
		return null;
	}
}
