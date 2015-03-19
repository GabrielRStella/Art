package com.ralitski.art.core.cmd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.ralitski.art.core.Controller;

public class CommandAnnotated implements Command {
	
	private Object host;
	private Method method;
	
	private String name;
	private String[] aliases;
	private String usage;
	private String[] help;
	
	public CommandAnnotated(Object host, Method m) {
		this.host = host;
		this.method = m;
		CommandMark mark = m.getAnnotation(CommandMark.class);
		this.name = mark.name();
		this.aliases = mark.aliases();
		this.usage = mark.usage();
		this.help = mark.help();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String[] getAliases() {
		return aliases;
	}

	@Override
	public String getUsage() {
		return usage;
	}

	@Override
	public String[] getHelp() {
		return help;
	}

	@Override
	public void execute(String alias, String args, Controller controller) {
		try {
			method.invoke(host, alias, args, controller);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
