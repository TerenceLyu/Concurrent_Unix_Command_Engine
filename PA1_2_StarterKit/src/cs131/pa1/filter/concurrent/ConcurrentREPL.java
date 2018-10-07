package cs131.pa1.filter.concurrent;

import com.sun.org.apache.xalan.internal.xsltc.dom.CurrentNodeListFilter;
import cs131.pa1.filter.Message;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class ConcurrentREPL {

	static String currentWorkingDirectory;
	
	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		Scanner s = new Scanner(System.in);
		System.out.print(Message.WELCOME);
		String command;
		LinkedList<Pair> jobs = new LinkedList<>();
		while(true) {
			//obtaining the command from the user
			System.out.print(Message.NEWCOMMAND);
			command = s.nextLine();
			if(command.equals("exit")) {
				break;
			} else if(command.equals("repl_jobs")) {
				int index = 0;
				Pair<ConcurrentFilter, String> curr = jobs.get(index);
				int i = 1;
				while(curr != null) {
					if(!curr.getKey().isDone()){
						System.out.println(i + ". " + curr.getValue() + " &");
						i ++;
					}
					index ++;
					if(index < jobs.size()){
						curr = jobs.get(index);
					}else{
						curr = null;
					}
				}
			} else if(command.contains("kill")) {
				command.trim();
				String subCommand = command.substring(5);
				int num = Integer.parseInt(subCommand);
				Pair<ConcurrentFilter, String> p = jobs.get(num - 1);
				p.getKey().done = true;
			} else if(!command.trim().equals("")) {
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
					if(filterlist.getNext() == null){
						Pair<ConcurrentFilter, String> p = new Pair(filterlist, command);
						jobs.add(p);
					}
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
