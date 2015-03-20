package com.ralitski.art.core;

import com.ralitski.art.core.cmd.CommandMark;
import com.ralitski.art.core.cmd.InvalidArgumentsException;

public class CmdSettings {
	
	@CommandMark(name = "extract",
			aliases = {"e, ex"},
			usage = "<code|script>",
			help = {"Extracts classes or scripts from the jar (or current directory)."})
	public void onExtract(String alias, String args, Controller controller) {
		if(args.equals("code")) {
			controller.getClassExtractor().run();
			controller.log("All classes have been extracted.");
		} else if(args.equals("script")) {
			controller.getScriptExtractor().run();
		} else {
			throw new InvalidArgumentsException("Extract takes an argument of either \"code\" or \"script\"");
		}
	}
}
