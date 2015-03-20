package com.ralitski.art.core;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArtClassLoader {
	
	private File dir;
	private Map<String, Class<?>> classes;
	
	public ArtClassLoader(File baseDir) {
		this.dir = baseDir;
		classes = new HashMap<>();
	}
	
	public Class<?> getClass(String name) {
		return classes.get(name);
	}
	
	public Set<String> getClassNames() {
		return classes.keySet();
	}
	
	//though it is a collection, there will be no duplicates
	public Collection<Class<?>> getClasses() {
		return classes.values();
	}
	
	public List<Class<?>> loadClasses() {
		URLClassLoader loader;
		try {
			loader = new URLClassLoader(new URL[]{dir.toURI().toURL()}, ArtClassLoader.class.getClassLoader());
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
			return null;
		}
		this.classes.clear();
		List<File> files = findClasses();
		if(files != null && !files.isEmpty()) {
			System.gc(); //clear old stuff; dunno if necessary
			List<Class<?>> classes = new LinkedList<Class<?>>();
			for(File f : files) {
				String name = getName(f);
				try {
					Class<?> c = loader.loadClass(name);
					classes.add(c);
					this.classes.put(c.getName(), c);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			return classes;
		}
		return null;
	}
	
	//worker methods
	
	//redirect for recursion
	private List<File> findClasses() {
		return findClasses(dir);
	}
	
	private List<File> findClasses(File dir) {
		if(dir.isDirectory()) {
			List<File> files = new LinkedList<>();
			for(File f : dir.listFiles()) {
				if(f.isDirectory()) {
					files.addAll(findClasses(f));
				} else if(f.getName().endsWith(".class")) {
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
		name = name.substring(1, name.length() - 6); //".class".length()
		name = name.replaceAll("\\\\", ".");
		return name;
	}
}
