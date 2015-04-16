package com.ralitski.art.core;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SubSettings extends Settings {
	
	private Settings parent;
	private String path;
	
	public SubSettings(Settings parent, String path) {
		this.parent = parent;
		if(!path.endsWith(".")) path = path.concat(".");
		this.path = path;
	}
	
	public Settings getParent() {
		return parent;
	}
	
	public Set<String> getKeys() {
		Set<String> keys = parent.getKeys();
		Set<String> realKeys = new HashSet<>();
		Iterator<String> iter = keys.iterator();
		String path = this.getTotalPath();
		while(iter.hasNext()) {
			String s = iter.next();
			if(s.startsWith(path)) {
				String s2 = s.substring(path.length());
				realKeys.add(s2);
			}
		}
		return realKeys;
	}
	
	public String getTotalPath() {
		if(parent instanceof SubSettings) {
			return ((SubSettings)parent).path + path;
		}
		return path;
	}
	
	public String getPath(String key) {
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
	
	public boolean hasSetting(String key) {
		return parent.hasSetting(getPath(key));
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
