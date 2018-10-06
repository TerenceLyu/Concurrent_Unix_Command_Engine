package cs131.pa1.filter.concurrent;

import java.util.concurrent.TimeUnit;

public class PrintFilter extends ConcurrentFilter {
	public PrintFilter() {
		super();
	}
	
	public void process() {
		while(!finished()) {
			processLine(this.input.poll());
		}
	}
	
	public String processLine(String line) {
		if (line != null){
			System.out.println(line);
		}
		return null;
	}
	
	@Override
	public void run() {
		this.process();
		this.done = true;
	}
}
