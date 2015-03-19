package com.ralitski.art.core;

import com.ralitski.art.core.cmd.CommandMark;

public class ConsoleCommands {
	
	@CommandMark(name = "extract",
			aliases = {"e, ex"},
			usage = "<\"code\"|\"script\">",
			help = {"Extracts classes or scripts from the jar (or current directory)"})
	public void onExtract(String alias, String args, Controller controller) {
		
	}
	
	@CommandMark(name = "delete",
			aliases = {"del"},
			usage = "<\"code\"|\"script\">",
			help = {"Deletes classes or scripts from the loading directory"})
	public void onDelete(String alias, String args, Controller controller) {
		
	}
	
	@CommandMark(name = "load",
			aliases = {"l, load"},
			usage = "<\"code\"|\"script\">",
			help = {"Loads classes or scripts from the loading directory"})
	public void onLoad(String alias, String args, Controller controller) {
		
	}
	
	@CommandMark(name = "list",
			aliases = {"li"},
			help = {"Lists the loaded classes and scripts"})
	public void onList(String alias, String args, Controller controller) {
		
	}
	
	@CommandMark(name = "draw",
			aliases = {"d, draw"},
			usage = "<\"code\"|\"script\"> <script name>",
			help = {"Draws the specified class or script"})
	public void onDraw(String alias, String args, Controller controller) {
		
	}
}
