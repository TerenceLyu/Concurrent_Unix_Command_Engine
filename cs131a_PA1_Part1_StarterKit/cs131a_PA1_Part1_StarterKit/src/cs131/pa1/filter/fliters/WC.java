package cs131.pa1.filter.fliters;
import cs131.pa1.filter.sequential.SequentialFilter;

import java.util.LinkedList;

public class WC extends SequentialFilter
{
	int line, word, character;

	public WC()
	{
		this.output = new LinkedList<>();
		this.line = 0;
		this.word = 0;
		this.character = 0;
	}

	@Override
	public void process()
	{
		while (!isDone())
		{
			String curr = input.poll();
			this.line ++;
			processLine(curr);
		}
		String result = this.line + " " + this.word + " " + this.character;
		output.add(result);
	}



	@Override
	protected String processLine(String curr)
	{
		//count character
		for (int i = 0; i < curr.length(); i++)
		{
			if(Character.isLetter(curr.charAt(i)))
			{
				character ++;
			}
		}
		//count word
		boolean isWord = false;
		int last = curr.length() - 1;

		for (int i = 0; i < curr.length(); i++)
		{
			if (Character.isLetter(curr.charAt(i)) && i != last)
			{
				isWord = true;
			}
			else if (!Character.isLetter(curr.charAt(i)) && isWord)
			{
				this.word++;
				isWord = false;
			}
			else if (Character.isLetter(curr.charAt(i)) && i == last)
			{
				this.word++;
			}
		}
		return null;
	}
}
