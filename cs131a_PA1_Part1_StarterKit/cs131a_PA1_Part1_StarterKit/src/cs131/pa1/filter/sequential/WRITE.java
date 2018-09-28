package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;
import java.io.*;
import java.util.LinkedList;

public class WRITE extends SequentialFilter
{
	String name;
	File f;
	
	public WRITE(String name){
		this.name = name;
		this.f = new File(name);
		this.output = new LinkedList<>();
	}
	@Override
	
	protected String processLine(String line)
	{
		try{
			PrintStream output = new PrintStream(f);
			output.println(line);
		}catch(Exception e)
		{
			System.out.println("bhl");
		}

		return null;
	}
}
