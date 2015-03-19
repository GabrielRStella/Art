package com.ralitski.art.core.cmd;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ralitski.art.api.Util;
import com.ralitski.art.core.Controller;

public class CommandHandler {
	
	public static String[] getHelpMessage(Command command) {
		String name = command.getName();
		String[] alias = command.getAliases();
		String[] help = command.getHelp();
		String usage = command.getUsage();
		int total = (alias != null ? 1 : 0) + help.length + 2;
		String[] msg = new String[total];
		int index = 0;
		msg[index++] = name;
		String usageMsg = "Usage: \"" + name;
		if(usage != null && !usage.isEmpty()) usageMsg = usageMsg + " " + usage;
		msg[index++] = usageMsg + "\"";
		if(alias != null) {
			String aliases = "Aliases: " + name + ", " + Util.combine(alias, ", ");
			msg[index++] = aliases;
		}
		for(String s : help) {
			msg[index++] = s;
		}
		return msg;
	}
	
	//
	
	private Controller controller;
	
	private Map<String, Command> commands;
	private List<Command> cmdList;
	
	public CommandHandler(Controller controller) {
		this.controller = controller;
		commands = Collections.synchronizedMap(new HashMap<String, Command>());
		cmdList = Collections.synchronizedList(new LinkedList<Command>());
		addCommand(new HelpCommand());
	}
	
	public void addCommand(Command command) {
		for(String alias : command.getAliases()) {
			commands.put(alias, command);
		}
		commands.put(command.getName(), command); //the formal name is not part of the aliases array
		cmdList.add(command);
	}
	
	//adds commands in the object, annotated with the @CommandMark annotation
	public void addCommands(Object commandList) {
		for(Method m : commandList.getClass().getDeclaredMethods()) {
			CommandMark mark = m.getAnnotation(CommandMark.class);
			if(mark != null) {
				Command c = new CommandAnnotated(commandList, m);
				addCommand(c);
			}
		}
	}

	public void handle(String cmd) {
		String[] parse = cmd.split(" ", 2);
		String alias = parse[0];
		String args = parse.length > 1 ? parse[1] : "";
		Command c = commands.get(alias);
		if(c != null) {
			try {
				c.execute(alias, args, controller);
			} catch (Exception e) {
				controller.log(e.getMessage());
			}
		} else {
			controller.log("Command \"" + cmd + "\" not recognized, type \"help\" for help");
		}
	}
	
	public Iterable<String> getHelpMessage() {
		return new HelpMessageIterable();
	}
	
	private class HelpCommand implements Command {
		
		private String[] alias = new String[]{"help", "?"};
		private String[] help = new String[]{"Prints the list of commands"};

		@Override
		public String getName() {
			return "help";
		}

		@Override
		public String[] getAliases() {
			return alias;
		}

		@Override
		public String getUsage() {
			return null;
		}

		@Override
		public String[] getHelp() {
			return help;
		}

		@Override
		public void execute(String alias, String args, Controller controller) {
			if(!args.isEmpty()) {
				if(args.equals("-a")) {
					//list all data
					controller.log("Detailed Command List:");
					for(String s : getHelpMessage()) {
						controller.log(s);
					}
				} else {
					//data for a specific command
					Command c = commands.get(args);
					if(c != null) {
						for(String s : getHelpMessage(c)) {
							controller.log(s);
						}
					} else {
						throw new CommandNotFoundException(args);
					}
				}
			} else {
				//list command names
				controller.log("Command List:");
				for(Command c : cmdList) {
					controller.log(c.getName());
				}
			}
		}
		
	}
	
	private class HelpMessageIterable implements Iterable<String> {

		@Override
		public Iterator<String> iterator() {
			return new HelpMessageIterator();
		}
		
	}
	
	private class HelpMessageIterator implements Iterator<String> {
		
		Iterator<Command> commands = cmdList.iterator();
		LinkedList<String> linesToGive = new LinkedList<String>();

		@Override
		public boolean hasNext() {
			return !linesToGive.isEmpty() || commands.hasNext();
		}

		@Override
		public String next() {
			if(linesToGive.isEmpty()) {
				if(commands.hasNext()) {
					linesToGive.addAll(Arrays.asList(getHelpMessage(commands.next())));
				} else {
					return null;
				}
			}
			return linesToGive.pollFirst();
		}

		@Override
		public void remove() {
			//no
		}
		
	}
}
