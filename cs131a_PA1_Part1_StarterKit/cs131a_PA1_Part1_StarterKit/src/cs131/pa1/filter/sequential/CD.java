package cs131.pa1.filter.sequential;
import java.io.File;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.LinkedList;
import cs131.pa1.filter.Message;

public class CD extends SequentialFilter {
	private String param;
	
	public CD(String param) {
		this.param = param;
		this.output = new LinkedList<>();
	}
	
	@Override
	public void process() {
		String[] path = SequentialREPL.currentWorkingDirectory.split(Pattern.quote(File.separator));
		if (param.equals(".")) {
			//do noting
		}else if (param.equals("..")) {
			//go one level up
			path = Arrays.copyOf(path, path.length-1);
			SequentialREPL.currentWorkingDirectory = String.join(FILE_SEPARATOR, path);
			
		}else if (new File(SequentialREPL.currentWorkingDirectory + FILE_SEPARATOR + param).isDirectory()) {
			//go to the specified directory
			SequentialREPL.currentWorkingDirectory += FILE_SEPARATOR + param;
		}else {
			System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter("cd "+param));
		}
		
	}
	
	@Override
	protected String processLine(String line) {
		return null;
	}
	
	@Override
	public String toString() {
		return "cd " + param;
	}
}
