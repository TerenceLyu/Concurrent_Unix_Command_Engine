package cs131.pa1.filter.concurrent;
import java.io.File;

public class LsFilter extends ConcurrentFilter{
	int counter;
	File folder;
	File[] flist;
	boolean done = false;
	
	public LsFilter() {
		super();
		counter = 0;
		folder = new File(ConcurrentREPL.currentWorkingDirectory);
		flist = folder.listFiles();
	}
	
	@Override
	public void process() {
		while(counter < flist.length) {
			output.add(processLine(""));
		}
		this.done = true;
	}
	
	@Override
	public String processLine(String line) {
		return flist[counter++].getName();
	}
	
	@Override
	public boolean isDone() {
		return this.done;
	}
	
	@Override
	public void run() {
		process();
	}
}
