package com.ralitski.art.core;

import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.ralitski.art.core.cmd.CommandHandler;
import com.ralitski.art.core.event.EventSystem;
import com.ralitski.art.core.events.EventCommand;
import com.ralitski.art.core.events.EventShutdown;
import com.ralitski.art.core.gui.GuiConsole;

/**
 * the main class of the program (that is actually instantiated).
 * @author ralitski
 *
 */
public class Controller {

	//settings keys
	public static final String KEY_CODE_PATH_DEST = "codePathDest";
	public static final String KEY_SCRIPT_PATH_DEST = "scriptPathDest";
	public static final String KEY_CODE_PATH_SRC = "codePathSrc";
	public static final String KEY_SCRIPT_PATH_SRC = "scriptPathSrc";
	public static final String KEY_SCRIPT_FILE_TYPE = "scriptFileType";
	//settings default values
	private static final String VALUE_CODE_PATH_DEST = "./code/";
	private static final String VALUE_SCRIPT_PATH_DEST = "./scripts/";
	private static final String VALUE_CODE_PATH_SRC = "com/ralitski/art/artists";
	private static final String VALUE_SCRIPT_PATH_SRC = "com/ralitski/art/scripts";
	private static final String VALUE_SCRIPT_FILE_TYPE = ".txt";
	
	//
	
	private static Controller instance;
	private static final String LOG_PREFIX = ">";
	private static final String LOG_INDENT = "   ";
	
	public static Controller getInstance() {
		return instance;
	}
	
	//
	
	private Settings settings;
	
	private GuiConsole gui;
	private Runnable classExtractor;
	private Runnable scriptExtractor;
	private ArtClassLoader artLoader;
	private ScriptLoader scriptLoader;
	private CommandHandler cmd;
	private EventSystem events;
	
	public Controller() {
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public Settings getSubSettings() {
		return settings.getSubSettings("controller");
	}
	
	public GuiConsole getGui() {
		return gui;
	}
	
	public Runnable getClassExtractor() {
		return classExtractor;
	}
	
	public Runnable getScriptExtractor() {
		return scriptExtractor;
	}
	
	public ArtClassLoader getClassLoader() {
		return artLoader;
	}
	
	public ScriptLoader getScriptLoader() {
		return scriptLoader;
	}
	
	public EventSystem getEventSystem() {
		return events;
	}
	
	public void setup() {
		instance = this;
		this.settings = new Settings();
		settings.load();

		Settings settings = getSubSettings();
		String codePathDest = settings.get(KEY_CODE_PATH_DEST, VALUE_CODE_PATH_DEST);
		String scriptPathDest = settings.get(KEY_SCRIPT_PATH_DEST, VALUE_SCRIPT_PATH_DEST);
		String codePathSrc = settings.get(KEY_CODE_PATH_SRC, VALUE_CODE_PATH_SRC);
		String scriptPathSrc = settings.get(KEY_SCRIPT_PATH_SRC, VALUE_SCRIPT_PATH_SRC);
		String fileTypeScript = settings.get(KEY_SCRIPT_FILE_TYPE, VALUE_SCRIPT_FILE_TYPE);
		String mainPath;
		//detect jar
		try {
			//this is not working; jar returns null, URI is "rsrc:./" and getPath() returns null
			mainPath = getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			//lame catch
			if(mainPath == null) mainPath = settings.get("JAR", "./art.jar");
		} catch (/*URISyntax*/Exception e) {
			//asume jar; this shouldn't occur
			e.printStackTrace();
			mainPath = "./art.jar";
		}
		//create extractors
		if(mainPath.endsWith(".jar")) {
			//jar
			classExtractor = new ExtractorJar(mainPath,
					codePathSrc,
					codePathDest,
					".class");
			scriptExtractor = new ExtractorJar(mainPath,
					scriptPathSrc,
					scriptPathDest,
					fileTypeScript);
		} else {
			//probably IDE
			classExtractor = new ExtractorFile(mainPath + codePathSrc, codePathDest, codePathSrc, ".class");
			scriptExtractor = new ExtractorFile(mainPath + scriptPathSrc, scriptPathDest, scriptPathSrc, fileTypeScript);
		}
		
		this.gui = new GuiConsole(this);
		gui.setup(settings);
		this.artLoader = new ArtClassLoader(new File(codePathDest));
		this.scriptLoader = new ScriptLoader(new File(scriptPathDest));
		
		this.cmd = new CommandHandler(this);
		cmd.addCommands(new CmdConsole());
		cmd.addCommand(new CmdSettings(this));
		cmd.addCommands(new CmdGui());
		hookSys();
		events = new EventSystem();
	}
	
	private void hookSys() {
		PrintStream out = new PrintStream(new GuiOutputStream(this), true);
		replace("out", out);
		replace("err", out);
	}
	
	private void replace(String name, PrintStream out) {
		try {
			Field f = System.class.getDeclaredField(name);
		    Field modifiersField = Field.class.getDeclaredField("modifiers");
		    modifiersField.setAccessible(true);
		    modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
			f.set(null, out);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		gui.log("Type \"help\" to list available commands.\n");
		dispatchCommand("gui");
		gui.start();
	}
	
	public void stop() {
		if(gui.running()) {
			gui.stop();
		}
		settings.save();
		events.callEvent(new EventShutdown());
	}
	
	public void dispatchCommand(String cmd) {
		if(cmd != null && !cmd.isEmpty()) {
			gui.log(LOG_PREFIX + cmd + "\n");
			events.callEvent(new EventCommand(cmd, true));
			this.cmd.handle(cmd);
			events.callEvent(new EventCommand(cmd, false));
		}
	}

	public void log(String s) {
		gui.log(LOG_INDENT + s + "\n");
	}
}
