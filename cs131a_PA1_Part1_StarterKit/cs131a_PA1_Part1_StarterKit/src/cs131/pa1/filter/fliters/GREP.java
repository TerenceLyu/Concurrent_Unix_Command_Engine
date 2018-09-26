package cs131.pa1.filter.fliters;
import cs131.pa1.filter.sequential.SequentialFilter;
public class GREP extends SequentialFilter
{
	String word;
	public GREP(String word)
	{
		this.word = word;
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
}
