package cs131.pa1.filter.concurrent;
//import java.util.LinkedList;
//import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import cs131.pa1.filter.Filter;


public abstract class ConcurrentFilter extends Filter implements Runnable{
	
	protected BlockingQueue<String> input;
	protected BlockingQueue<String> output;
	protected boolean done = false;
	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}
	
	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof ConcurrentFilter){
			ConcurrentFilter sequentialNext = (ConcurrentFilter) nextFilter;
			this.next = sequentialNext;
			sequentialNext.prev = this;
			if (this.output == null){
				this.output = new LinkedBlockingQueue<>();
			}
			sequentialNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}
	
	public Filter getNext() {
		return next;
	}
	
	public void process() {
		while (!this.isDone()){
			String line = null;
			try{
				line = input.poll(100, TimeUnit.MILLISECONDS);
			}catch (InterruptedException e){}
			
			String processedLine = null;
			if (line != null){
				processedLine = processLine(line);
			}
			
			if (processedLine != null){
				output.add(processedLine);
			}
		}	
	}
	
	@Override
	public boolean isDone() {
		return input.size() == 0;
	}
	
	protected abstract String processLine(String line);
	
}
