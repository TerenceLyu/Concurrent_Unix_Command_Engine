package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;
import java.io.*;
import java.util.LinkedList;
import java.nio.file.*;

public class WRITE extends SequentialFilter
{
	String name;
	public WRITE(String name){
		this.name = SequentialREPL.currentWorkingDirectory + this.FILE_SEPARATOR + name;
		try
		{
			Writer fileWriter = new FileWriter(this.name, false);
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		this.output = new LinkedList<>();
	}
	@Override
	
	protected String processLine(String line)
	{
		
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(name, true));//Set true for append mode
			writer.write(line);
			writer.newLine();   //Add new line
			writer.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
	
	@Override
	public String toString()
	{
		return "> " + name;
	}
}
