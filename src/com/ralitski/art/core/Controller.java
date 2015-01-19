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
	private Extractor extractor;
	private ArtClassLoader loader;
	
	public Controller() {
		instance = this;
		this.settings = new Settings();
		settings.load();
		this.gui = new Gui(this);
		extractor = new Extractor(this);
		this.loader = new ArtClassLoader(new File(settings.get("CODE_PATH", "./code/")));
	}
	
	public Settings getSettings() {
		return settings;
	}
	
	public Gui getGui() {
		return gui;
	}
	
	public Extractor getExtractor() {
		return extractor;
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
