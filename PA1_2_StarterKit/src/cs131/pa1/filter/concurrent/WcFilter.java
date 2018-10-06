package cs131.pa1.filter.concurrent;

public class WcFilter extends ConcurrentFilter {
	private int linecount;
	private int wordcount;
	private int charcount;
	
	public WcFilter() {
		super();
	}
	
	public void process() {
		super.process();
		output.add(processLine(null));
	}
	
	public String processLine(String line) {
		//prints current result if ever passed a null
		if(line == null) {
			return linecount + " " + wordcount + " " + charcount;
		}
		
		linecount++;
		String[] wct = line.split(" ");
		wordcount += wct.length;
		String[] cct = line.split("|");
		charcount += cct.length;
		return null;
	}
	
	@Override
	public void run() {
		this.process();
		this.done = true;
	}
}
