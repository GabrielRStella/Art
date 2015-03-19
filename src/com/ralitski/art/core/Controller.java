package com.ralitski.art.core;

import java.io.File;
import java.net.URISyntaxException;

import com.ralitski.art.core.cmd.CommandHandler;
import com.ralitski.art.core.gui.Gui;

/**
 * the main class of the program (that is actually instantiated).
 * @author ralitski
 *
 */
public class Controller {
	
	private static Controller instance;
	private static final String LOG_PREFIX = ">";
	
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

		settings = settings.getSubSettings("controller");
		String codePath = settings.get("CODE_PATH_DEST", "./code/");
		String scriptPath = settings.get("SCRIPT_PATH_DEST", "./scripts/");
		String codePathSrc = settings.get("CODE_PATH_SRC", "com/ralitski/art/artists");
		String scriptPathSrc = settings.get("SCRIPT_PATH_SRC", "com/ralitski/art/scripts");
		String fileTypeScript = settings.get("SCRIPT_FILE_TYPE", ".txt");
		String mainPath;
		//detect jar
		try {
			mainPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (URISyntaxException e) {
			//asume jar
			e.printStackTrace();
			mainPath = "./art.jar";
		}
		//create extractors
		if(mainPath.endsWith(".jar")) {
			//jar
			classExtractor = new ExtractorJar(mainPath,
					codePathSrc,
					codePath,
					".class");
			scriptExtractor = new ExtractorJar(mainPath,
					scriptPathSrc,
					scriptPath,
					fileTypeScript);
		} else {
			//probably IDE
			classExtractor = new ExtractorFile(mainPath + codePathSrc, codePath, codePathSrc, ".class");
			scriptExtractor = new ExtractorFile(mainPath + scriptPathSrc, scriptPath, scriptPathSrc, fileTypeScript);
		}
		
		this.gui = new Gui(this);
		this.artLoader = new ArtClassLoader(new File(codePath));
		this.scriptLoader = new ScriptLoader(new File(scriptPath));
		this.cmd = new CommandHandler(this);
		gui.setup(settings);
	}
	
	public void start() {
		gui.start();
	}
	
	public void stop() {
		settings.save();
	}
	
	public void dispatchCommand(String cmd) {
		if(cmd != null && !cmd.isEmpty()) {
			log(LOG_PREFIX + cmd);
			this.cmd.handle(cmd);
		}
	}

	public void log(String s) {
		gui.log(s + "\n");
	}
}
