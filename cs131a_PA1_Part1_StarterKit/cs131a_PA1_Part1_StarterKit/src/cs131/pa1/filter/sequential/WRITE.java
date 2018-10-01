package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;
import java.io.*;
import java.util.LinkedList;
import java.nio.file.*;

public class WRITE extends SequentialFilter
{
	String name;
	String path;
	public WRITE(String name){
		this.name = name;
		this.path = SequentialREPL.currentWorkingDirectory + this.FILE_SEPARATOR + this.name;
		try
		{
			Writer fileWriter = new FileWriter(this.path, false);
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
			BufferedWriter writer = new BufferedWriter(new FileWriter(this.path, true));//Set true for append mode
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
