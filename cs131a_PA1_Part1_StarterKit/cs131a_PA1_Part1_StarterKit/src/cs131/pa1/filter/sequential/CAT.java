package cs131.pa1.filter.sequential;
import cs131.pa1.filter.Message;
import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class CAT extends SequentialFilter
{

	private String fileName;
	public CAT(String fileName)
	{
		this.fileName = fileName;
		this.output = new LinkedList<>();
	}
	@Override
	public void process()
	{
//		System.out.println(new File(".").getAbsoluteFile());
		File f = new File(this.fileName);
		
		Scanner input;
		try
		{
			Scanner scanner = new Scanner(f);
			while (scanner.hasNextLine())
			{
				this.output.add(scanner.nextLine());
			}
		} catch (Exception e)
		{
//			e.printStackTrace();
			System.out.print(Message.FILE_NOT_FOUND.with_parameter("cat " + this.fileName));
			SequentialREPL.error = true;
		}
	}

	@Override
	protected String processLine(String line)
	{
		return null;
	}
}
