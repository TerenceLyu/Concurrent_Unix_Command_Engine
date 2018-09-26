package cs131.pa1.filter.fliters;
import cs131.pa1.filter.Message;
import cs131.pa1.filter.sequential.SequentialFilter;
import java.io.File;
import java.util.Scanner;

public class CAT extends SequentialFilter
{

	private String fileName;
	public CAT(String fileName)
	{
		this.fileName = fileName;
	}
	@Override
	public void process()
	{
		File f = new File(this.fileName);
		Scanner input;
		try
		{
			input = new Scanner(f);
			while (input.hasNextLine())
			{
				this.output.add(input.nextLine());
			}
		} catch (Exception e)
		{
			System.out.print(Message.FILE_NOT_FOUND.with_parameter(this.fileName));
		}
	}

	@Override
	protected String processLine(String line)
	{
		return null;
	}
}
