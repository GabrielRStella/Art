package com.ralitski.art.core.cmd;

import com.ralitski.art.core.Controller;

//only getAliases() and getUsage() may return null
public interface Command {
	String getName();
	String[] getAliases();
	String getUsage();
	String[] getHelp();
	void execute(String alias, String args, Controller controller);
}
