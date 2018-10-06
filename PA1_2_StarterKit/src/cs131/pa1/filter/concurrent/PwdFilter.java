package cs131.pa1.filter.concurrent;

public class PwdFilter extends ConcurrentFilter {
	boolean done = false;
	public PwdFilter() {
		super();
	}
	
	public void process() {
		output.add(processLine(""));
		this.done = true;
	}
	
	public String processLine(String line) {
		return ConcurrentREPL.currentWorkingDirectory;
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
