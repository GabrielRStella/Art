package com.ralitski.art.core;

import java.io.File;
import java.net.URISyntaxException;

import com.ralitski.art.core.gui.Gui;

/**
 * the main class of the program (that is actually instantiated).
 * @author ralitski
 *
 */
public class Controller {
	
	private static Controller instance;
	
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
		String codePath = settings.get("PATH_CODE", "./code/");
		String scriptPath = settings.get("PATH_SCRIPT", "./scripts/");
		String fileTypeScript = settings.get("SCRIPT_FILE_TYPE", ".txt");
		String mainPath;
		try {
			mainPath = Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		} catch (URISyntaxException e) {
			//asume jar
			e.printStackTrace();
			mainPath = "./art.jar";
		}
		if(mainPath.endsWith(".jar")) {
			//jar
			classExtractor = new ExtractorJar(mainPath,
					settings.get("PATH_CODE_IN_JAR", "com/ralitski/art/artists"),
					codePath,
					".class");
			scriptExtractor = new ExtractorJar(mainPath,
					settings.get("PATH_SCRIPT_IN_JAR", "com/ralitski/art/scripts"),
					scriptPath,
					fileTypeScript);
		} else {
			//probably IDE
			classExtractor = new ExtractorFile(mainPath, codePath, ".class");
		}
		
		this.gui = new Gui(this);
		this.artLoader = new ArtClassLoader(new File(codePath));
		this.scriptLoader = new ScriptLoader(new File(scriptPath));
	}
	
	public void start() {
		gui.setup();
		gui.start();
	}
	
	public void stop() {
		settings.save();
	}
	
	public void dispatchCommand(String cmd) {
		if(cmd != null && !cmd.isEmpty()) {
			gui.log(cmd + "\n");
		}
	}
}
