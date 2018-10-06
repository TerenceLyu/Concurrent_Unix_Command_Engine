package cs131.pa1.filter.concurrent;

import java.util.concurrent.TimeUnit;

public class PrintFilter extends ConcurrentFilter {
	public PrintFilter() {
		super();
	}
	
	public void process() {
		while(!isDone()) {
			try {
				processLine(input.poll(100, TimeUnit.MILLISECONDS));
			}catch (InterruptedException e) {}
		}
	}
	
	@Override
	public boolean isDone() {
		return this.prev.isDone() && super.isDone();
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
	}
}
