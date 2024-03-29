package cs131.pa1.filter.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import cs131.pa1.filter.Filter;


public abstract class ConcurrentFilter extends Filter implements Runnable{
	
	protected BlockingQueue<String> input;
	protected BlockingQueue<String> output;
	boolean done = false;
	
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
		while (!finished()&&!isDone()){
			String line = input.poll();
			String processedLine = null;
			if (line != null){
				processedLine = processLine(line);
			}
			
			if (processedLine != null){
				output.add(processedLine);
			}
		}	
	}
	public void kill(){
		this.done = true;
	}
	@Override
	public boolean isDone() {
		return this.done;
	}
	
	public boolean finished() {
		return this.prev.isDone() && this.input.size() == 0;
	}
	protected abstract String processLine(String line);
	
}
