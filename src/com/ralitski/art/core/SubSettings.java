package com.ralitski.art.core;

import java.io.File;
import java.io.IOException;

public class SubSettings extends Settings {
	
	private Settings parent;
	private String path;
	
	public SubSettings(Settings parent, String path) {
		this.parent = parent;
		if(!path.endsWith(".")) path = path.concat(".");
		this.path = path;
	}
	
	private String getPath(String key) {
		return path + key;
	}
	
	public String get(String key) {
		return parent.get(getPath(key));
	}
	
	public String get(String key, String defaultValue) {
		return parent.get(getPath(key), defaultValue);
	}
	
	public String set(String key, String value) {
		return parent.set(getPath(key), value);
	}
	
	public void save() {
		parent.save();
	}
	
	public void save(String f) throws IOException {
		parent.save(f);
	}
	
	public void save(File f) throws IOException {
		parent.save(f);
	}
	
	public void load() {
		parent.load();
	}
	
	public void load(String f) throws IOException {
		parent.load(f);
	}
	
	public void load(File f) throws IOException {
		parent.load(f);
	}
}
