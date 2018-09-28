package cs131.pa1.filter.sequential;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import cs131.pa1.filter.Message;

public class CD extends SequentialFilter
{
	private String param;
	
	public CD(String param)
	{
		this.param = param;
	}
	
	@Override
	public void process()
	{
		String[] path = SequentialREPL.currentWorkingDirectory.split(FILE_SEPARATOR);
		if (param.equals("."))
		{
			//do noting
		}else if (param.equals(".."))
		{
			//go one level up
			path = Arrays.copyOf(path, path.length-1);
			SequentialREPL.currentWorkingDirectory = String.join(FILE_SEPARATOR, path);
			
		}else if (new File(param).isDirectory())
		{
			//go to the specified directory
			SequentialREPL.currentWorkingDirectory += FILE_SEPARATOR + param;
		}else
		{
			this.output = new LinkedList<>();
			output.add(Message.DIRECTORY_NOT_FOUND.with_parameter("cd "+param));
		}
		
	}
	
	@Override
	protected String processLine(String line)
	{
		return null;
	}
}
