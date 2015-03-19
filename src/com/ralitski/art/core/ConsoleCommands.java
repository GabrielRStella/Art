package com.ralitski.art.core;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.ralitski.art.api.Util;
import com.ralitski.art.core.cmd.CommandMark;
import com.ralitski.art.core.cmd.InvalidArgumentsException;

public class ConsoleCommands {
	
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
	
	@CommandMark(name = "delete",
			aliases = {"del"},
			usage = "<code|script>",
			help = {"Deletes classes or scripts from the loading directory."})
	public void onDelete(String alias, String args, Controller controller) {
		if(args.equals("code")) {
			String dest = controller.getSubSettings().get(Controller.KEY_CODE_PATH_DEST);
			File dir = new File(dest);
			Util.delete(dir);
		} else if(args.equals("script")) {
			String dest = controller.getSubSettings().get(Controller.KEY_SCRIPT_PATH_DEST);
			File dir = new File(dest);
			Util.delete(dir);
		} else {
			throw new InvalidArgumentsException("Delete takes an argument of either \"code\" or \"script\"");
		}
	}
	
	@CommandMark(name = "load",
			aliases = {"l, load"},
			usage = "<code|script>",
			help = {"Loads classes or scripts from the loading directory."})
	public void onLoad(String alias, String args, Controller controller) {
		if(args.equals("code")) {
			ArtClassLoader loader = controller.getClassLoader();
			List<Class<?>> classes = loader.loadClasses();
			if(classes != null) {
				for(Class<?> c : classes) {
					String append = ArtCreatorClass.isArtist(c) ? "" : "   (Not Artist)";
					controller.log("Loaded class: " + c.getName() + append);
				}
				controller.log("All classes have been loaded.");
			} else {
				controller.log("No classes detected.");
			}
		} else if(args.equals("script")) {
			ScriptLoader loader = controller.getScriptLoader();
			List<String> scripts = loader.loadScripts();
			if(scripts != null) {
				for(String s : scripts) {
					controller.log("Loaded script: " + s);
				}
				controller.log("All scripts have been loaded.");
			} else {
				controller.log("No scripts detected.");
			}
		} else {
			throw new InvalidArgumentsException("Load takes an argument of either \"code\" or \"script\"");
		}
	}
	
	@CommandMark(name = "list",
			aliases = {"li"},
			usage = "[-a]",
			help = {"Lists the loaded classes and scripts."})
	public void onList(String alias, String args, Controller controller) {
		String in = "  ";
		Collection<Class<?>> classes = controller.getClassLoader().getClasses();
		if(classes.isEmpty()) {
			controller.log("No classes have been loaded");
		} else {
			if(args.equals("-a")) {
				//displays all classes, not just artists
				controller.log("Loaded Artist Classes:");
				for(Class<?> c : classes) {
					String append = ArtCreatorClass.isArtist(c) ? "" : "   (Not Artist)";
					controller.log(in + c.getName() + append);
				}
			} else {
				//displays only artist classes
				controller.log("Loaded Classes:");
				for(Class<?> c : classes) {
					if(ArtCreatorClass.isArtist(c)) {
						controller.log(in + c.getName());
					}
				}
			}
		}
		Set<String> scripts = controller.getScriptLoader().getScripts();
		if(scripts.isEmpty()) {
			controller.log("No scripts have been loaded");
		} else {
			controller.log("Loaded Scripts:");
			for(String s : scripts) {
				controller.log(in + s);
			}
		}
	}
	
	@CommandMark(name = "draw",
			aliases = {"d, draw"},
			usage = "[-c -s] <artist name>",
			help = {"Draws the specified class or script.","The -c flag attaches the default code package prefix.","The -s flag attaches the default script package prefix."})
	public void onDraw(String alias, String args, Controller controller) {
		//attach prefixed
		if(args.startsWith("-c")) {
			args = controller.getSettings().get("PACKAGE_CODE", "com.ralitski.art.artists.") + args.substring(3);
		} else if(args.startsWith("-s")) {
			args = controller.getSettings().get("PACKAGE_SCRIPT", "com.ralitski.art.scripts.") + args.substring(3);
		}
		Class<?> c = controller.getClassLoader().getClass(args);
		if(c != null) {
			if(ArtCreatorClass.isArtist(c)) {
				controller.log("Drawing art: " + args);
				ArtManager.createArt(controller, c);
			} else {
				controller.log("The specified class \"" + args + "\" was detected, but is not an artist.");
			}
		} else {
			String s = controller.getScriptLoader().getScript(args);
			if(s != null) {
				controller.log("Drawing art: " + args);
				ArtManager.createArt(controller, args, s);
			} else {
				controller.log("The specified class \"" + args + "\" was not detected.");
			}
		}
	}
}
