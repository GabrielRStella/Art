package com.ralitski.art.core;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
	
	public List<String> getComments(String key) {
		return parent.getComments(getPath(key));
	}
	
	/**
	 * 
	 * @param key
	 * @param comment
	 * @return if the key was found and the comment was added (or previously there)
	 */
	public boolean addComment(String key, String comment) {
		return parent.addComment(getPath(key), comment);
	}
	
	public void removeComments(String key) {
		parent.removeComments(getPath(key));
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
