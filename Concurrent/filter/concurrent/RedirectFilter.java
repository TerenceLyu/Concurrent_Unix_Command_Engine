package cs131.pa1.filter.concurrent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class RedirectFilter extends ConcurrentFilter {
	private FileWriter fw;
	
	public RedirectFilter(String line) throws Exception {
		super();
		String[] param = line.split(">");
		if(param.length > 1) {
			if(param[1].trim().equals("")) {
				System.out.printf(Message.REQUIRES_PARAMETER.toString(), line.trim());
				throw new Exception();
			}
			try {
				fw = new FileWriter(new File(ConcurrentREPL.currentWorkingDirectory + Filter.FILE_SEPARATOR + param[1].trim()));
			} catch (IOException e) {
				System.out.printf(Message.FILE_NOT_FOUND.toString(), line);	//shouldn't really happen but just in case
				throw new Exception();
			}
		} else {
			System.out.printf(Message.REQUIRES_INPUT.toString(), line);
			throw new Exception();
		}
	}
	
	public void process() {
		while(!finished()&&!isDone()) {
			processLine(input.poll());
		}
		try
		{
			fw.flush();
			fw.close();
		}catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	
	public String processLine(String line) {
		if (line != null){
			try {
				fw.append(line + "\n");
			} catch (IOException e) {
				System.out.printf(Message.FILE_NOT_FOUND.toString(), line);
			}
		}
		return null;
	}
	
	@Override
	public void run() {
		process();
		this.done = true;
	}
}
