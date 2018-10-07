package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Message;
import java.util.Scanner;

public class ConcurrentREPL {

	static String currentWorkingDirectory;
	
	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		Scanner s = new Scanner(System.in);
		System.out.print(Message.WELCOME);
		String command;
		while(true) {
			//obtaining the command from the user
			System.out.print(Message.NEWCOMMAND);
			command = s.nextLine();
			if(command.equals("exit")) {
				break;
			}
//			else if(command.equals("repl_jobs")) {
//
//			}
			else if(!command.trim().equals("")) {
				boolean backGround;
				if(command.charAt(command.length() - 1) == '&'){
					backGround = true;
					command = command.substring(0, command.length() - 2);
					command.trim();
				}else {
					backGround = false;
				}
				//building the filters list from the command
				ConcurrentFilter filterlist = ConcurrentCommandBuilder.createFiltersFromCommand(command);
				Thread thd = null;
				while(filterlist != null) {
					thd = new Thread(filterlist);
					thd.start();
					filterlist = (ConcurrentFilter) filterlist.getNext();
				}
				if(!backGround) {
					if (thd != null) {
						try{
							thd.join();
						}catch (InterruptedException e){}
					}
				}
			}
		}
		s.close();
		System.out.print(Message.GOODBYE);
	}

}
