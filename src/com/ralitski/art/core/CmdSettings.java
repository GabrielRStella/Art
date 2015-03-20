package com.ralitski.art.core;

import java.util.HashSet;
import java.util.Set;

import com.ralitski.art.core.cmd.Command;
import com.ralitski.art.core.cmd.CommandHandler;
import com.ralitski.art.core.cmd.CommandMark;

//TODO: command to look at comments
public class CmdSettings implements Command {
	
	private Settings base;
	private Settings current;
	private CommandHandler subCmd;

	private String[] alias = new String[]{"s"};
	private String[] help = new String[]{"Allows you to interact with the program's settings."};
	
	public CmdSettings(Controller controller) {
		this.base = this.current = controller.getSettings();
		subCmd = new CommandHandlerCustom(controller);
		subCmd.addCommands(this);
	}

	@Override
	public String getName() {
		return "settings";
	}

	@Override
	public String[] getAliases() {
		return alias;
	}

	@Override
	public String getUsage() {
		return "<get|set|sub|sup|base|list|save|load|current>";
	}

	@Override
	public String[] getHelp() {
		return help;
	}

	@Override
	public void execute(String alias, String args, Controller controller) {
		subCmd.handle(args); //it's that easy! wow!
	}
	
	/*
	 * enter: commands
	 */
	
	@CommandMark(name = "get",
			usage = "<key>",
			help = {"Returns the value associated with the given key"})
	public void onGet(String alias, String args, Controller controller) {
		if(current.hasSetting(args)) {
			controller.log("Value for key \"" + args + "\":");
			controller.log(current.get(args));
		} else {
			controller.log("No value set for \"" + args + "\"");
		}
	}
	
	@CommandMark(name = "set",
			usage = "<key> <value>",
			help = {"Sets the value associated with the given key"})
	public void onSet(String alias, String args, Controller controller) {
		String[] parse = args.split(" ", 2);
		if(parse.length == 2) {
			String key = parse[0];
			String value = parse[1];
			boolean hasOld = current.hasSetting(key);
			if(hasOld) {
				String old = current.set(key, value);
				controller.log("Setting \"" + key + "\" changed from \"" + old + "\" to \"" + value + "\"");
			} else {
				controller.log("Setting \"" + key + "\" set to \"" + value + "\"");
			}
		} else {
			controller.log("Set requires a key and value.");
		}
	}
	
	@CommandMark(name = "sub",
			usage = "<section>",
			help = {"Moves the interface down to the specified section."})
	public void onSub(String alias, String args, Controller controller) {
		current = current.getSubSettings(args);
		controller.log("Moved to section " + getName(current));
	}
	
	@CommandMark(name = "sup",
			help = {"Moves the interface up to the parent of the current subsection, if possible."})
	public void onSup(String alias, String args, Controller controller) {
		if(current instanceof SubSettings) {
			current = ((SubSettings)current).getParent();
			controller.log("Moved to section" + getName(current));
		} else {
			controller.log("Already at root section.");
		}
	}
	
	@CommandMark(name = "base",
			help = {"Returns the settings interface to the root section"})
	public void onBase(String alias, String args, Controller controller) {
		current = base;
		controller.log("Returned to base section.");
	}
	
	@CommandMark(name = "list",
			usage = "[-a]",
			help = {"Lists the values stored in the settings.","The optional -a flag returns the raw list, without subsections considered."})
	public void onList(String alias, String args, Controller controller) {
		if(args.equals("-a")) {
			controller.log("All keys:");
			for(String s : current.getKeys()) {
				controller.log("   " + s);
			}
		} else {
			Set<String> keys = current.getKeys();
			Set<String> realKeys = new HashSet<>();
			for(String key : keys) {
				if(!key.contains(".")) {
					realKeys.add("   " + key);
				}
			}
			if(realKeys.isEmpty()) {
				controller.log("No keys found.");
			} else {
				controller.log("Keys:");
				for(String key : realKeys) {
					controller.log(key);
				}
			}
			Set<String> sub = current.getSections();
			if(sub.isEmpty()) {
				controller.log("No subsections found.");
			} else {
				controller.log("Subsections:");
				for(String s : sub) {
					controller.log("   " + s);
				}
			}
		}
	}
	
	@CommandMark(name = "save",
			usage = "<key> <value>",
			help = {"Sets the value associated with the given key"})
	public void onSave(String alias, String args, Controller controller) {
		base.save();
	}
	
	@CommandMark(name = "load",
			usage = "<key> <value>",
			help = {"Sets the value associated with the given key"})
	public void onLoad(String alias, String args, Controller controller) {
		base.load();
	}
	
	@CommandMark(name = "current",
			aliases={"cur"},
			help = {"Prints the current settings subsection"})
	public void onCurrent(String alias, String args, Controller controller) {
		String s = getName(current);
		controller.log("Current subsection: " + s);
	}
	
	private String getName(Settings settings) {
		return settings instanceof SubSettings ? ((SubSettings)settings).getTotalPath() : "Base";
	}
	
	/*
	 * custom command handler to change the "command not found" message
	 */
	
	public class CommandHandlerCustom extends CommandHandler {
		
		public CommandHandlerCustom(Controller controller) {
			super(controller);
		}

		protected void cmdNotFound(String cmd) {
			handle("help");
		}
	}
}
