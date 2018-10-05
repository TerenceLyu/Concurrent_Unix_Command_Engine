package cs131.pa1.filter.concurrent;

public class PrintFilter extends ConcurrentFilter {
	public PrintFilter() {
		super();
	}
	
	public void process() {
		while(!isDone()) {
			processLine(input.poll());
		}
	}
	
	@Override
	public boolean isDone() {
		return this.prev.isDone() && super.isDone();
	}
	
	public String processLine(String line) {
		System.out.println(line);
		return null;
	}
	
	@Override
	public void run() {
		this.process();
	}
}
