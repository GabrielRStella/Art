package com.ralitski.art.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ScriptLoader {
	
	private File dir;
	
	private Map<String, String> scripts;

	public ScriptLoader(File file) {
		this.dir = file;
		scripts = new HashMap<>();
	}

	public Set<String> getScripts() {
		return scripts.keySet();
	}
	
	public String getScript(String name) {
		return scripts.get(name);
	}
	
	public List<String> loadScripts() {
		this.scripts.clear();
		List<File> files = findScripts();
		if(files != null && !files.isEmpty()) {
			List<String> scripts = new LinkedList<>();
			for(File f : files) {
				String name = getName(f);
				String script = read(f);
				if(script != null) {
					this.scripts.put(name, script);
					scripts.add(name);
				}
			}
			return scripts;
		}
		return null;
	}
	
	private List<File> findScripts() {
		return findScripts(dir);
	}
	
	private List<File> findScripts(File dir) {
		if(dir.isDirectory()) {
			List<File> files = new LinkedList<>();
			for(File f : dir.listFiles()) {
				if(f.isDirectory()) {
					files.addAll(findScripts(f));
				} else if(f.getName().endsWith(".txt")) {
					files.add(f);
				}
			}
			return files;
		} else {
			return null;
		}
	}
	
	private String getName(File file) {
		String name = file.getAbsolutePath().substring(dir.getAbsolutePath().length());
		name = name.substring(1, name.length() - 4); //".txt".length()
		name = name.replaceAll("\\\\", ".");
		return name;
	}
	
	private String read(File f) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			String line;
			String script = "";
			while((line = reader.readLine()) != null) {
				script = script + line + "\n";
			}
			reader.close();
			return script;
		} catch (FileNotFoundException e) {
			//file was detected on system, won't happen
			return null;
		} catch (IOException e) {
			System.out.println("Unable to load script from " + f.getPath());
			return null;
		}
	}
}
