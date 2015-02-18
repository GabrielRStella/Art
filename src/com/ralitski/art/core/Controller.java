package com.ralitski.art.core;

import java.io.File;

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
	private Extractor classExtractor;
	private Extractor scriptExtractor;
	private ArtClassLoader artLoader;
	private ScriptLoader scriptLoader;
	
	public Controller() {
		instance = this;
		this.settings = new Settings();
		settings.load();
		
		String jarPath = settings.get("PATH_JAR", "./art.jar");
		String codePath = settings.get("PATH_CODE", "./code/");
		String scriptPath = settings.get("PATH_SCRIPT", "./scripts/");
		classExtractor = new Extractor(jarPath,
				settings.get("PATH_CODE_IN_JAR", "com/ralitski/art/artists"),
				codePath,
				".class");
		
		scriptExtractor = new Extractor(jarPath,
				settings.get("PATH_SCRIPT_IN_JAR", "com/ralitski/art/scripts"),
				scriptPath,
				settings.get("SCRIPT_FILE_TYPE", ".txt"));
		
		this.gui = new Gui(this);
		this.artLoader = new ArtClassLoader(new File(codePath));
		this.scriptLoader = new ScriptLoader(new File(scriptPath));
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public Gui getGui() {
		return gui;
	}
	
	public Extractor getClassExtractor() {
		return classExtractor;
	}
	
	public Extractor getScriptExtractor() {
		return scriptExtractor;
	}
	
	public ArtClassLoader getClassLoader() {
		return artLoader;
	}
	
	public ScriptLoader getScriptLoader() {
		return scriptLoader;
	}
	
	public void start() {
		gui.setup();
		gui.start();
	}
	
	public void stop() {
		settings.save();
	}
}
