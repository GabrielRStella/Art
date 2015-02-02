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
	private ArtClassLoader loader;
	
	public Controller() {
		instance = this;
		this.settings = new Settings();
		settings.load();
		
		String jarPath = settings.get("PATH_JAR", "./art.jar");
		String codePath = settings.get("PATH_CODE", "./code/");
		classExtractor = new Extractor(jarPath,
				settings.get("PATH_CODE_IN_JAR", "com/ralitski/art/artists"),
				codePath);
		
		scriptExtractor = new Extractor(jarPath,
				settings.get("PATH_SCRIPT_IN_JAR", "com/ralitski/art/scripts"),
				settings.get("PATH_SCRIPT", "./scripts/"));
		
		this.gui = new Gui(this);
		this.loader = new ArtClassLoader(new File(codePath));
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
		return loader;
	}
	
	public void start() {
		gui.setup();
		gui.start();
	}
	
	public void stop() {
		settings.save();
	}
}
