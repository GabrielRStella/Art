package com.ralitski.art.core;

import java.io.File;

import com.ralitski.art.core.cmd.CommandHandler;
import com.ralitski.art.core.gui.Gui;

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
	
	private Gui gui;
	private Runnable classExtractor;
	private Runnable scriptExtractor;
	private ArtClassLoader artLoader;
	private ScriptLoader scriptLoader;
	private CommandHandler cmd;
	
	public Controller() {
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public Settings getSubSettings() {
		return settings.getSubSettings("controller");
	}
	
	public Gui getGui() {
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
		
		this.gui = new Gui(this);
		gui.setup(settings);
		this.artLoader = new ArtClassLoader(new File(codePathDest));
		this.scriptLoader = new ScriptLoader(new File(scriptPathDest));
		
		this.cmd = new CommandHandler(this);
		cmd.addCommands(new ConsoleCommands());
	}
	
	public void start() {
		gui.start();
	}
	
	public void stop() {
		settings.save();
	}
	
	public void dispatchCommand(String cmd) {
		if(cmd != null && !cmd.isEmpty()) {
			gui.log(LOG_PREFIX + cmd + "\n");
			this.cmd.handle(cmd);
		}
	}

	public void log(String s) {
		gui.log(LOG_INDENT + s + "\n");
	}
}
