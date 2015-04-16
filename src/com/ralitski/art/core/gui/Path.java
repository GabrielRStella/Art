package com.ralitski.art.core.gui;

import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

/**
 * Used for organizing components in a top container. Automatically handles Containers (as "directories" in the system).
 * 
 * @author ralitski
 */
public class Path {
	
	private Directory map;
	
	public Path(Container top) {
		map = new Directory(top);
	}
	
	private Directory getDirectory(String[] path) {
		if(path.length == 0) {
			return map;
		} else {
			int i = 0;
			Directory d = map;
			while(i < path.length) {
				String dir = path[i++];
				Object o = d.sub.get(dir);
				if(o instanceof Directory) {
					if(i == path.length) {
						return (Directory)o;
					} else {
						d = (Directory)o;
					}
				} else {
					//no component there, directory ends
					break;
				}
			}
			return null;
		}
	}
	
	public Component getComponent(String path) {
		if(path.isEmpty()) {
			return map.container;
		}
		if(path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		int i = path.lastIndexOf("/");
		if(i == -1) {
			Object o = map.sub.get(path);
			return get(o);
		} else {
			String parent = path.substring(0, i);
			String name = path.substring(i + 1, path.length());
			Directory d = getDirectory(parent.split("/"));
			if(d != null) {
				return get(d.sub.get(name));
			} else {
				return null;
			}
		}
	}
	
	public Component addComponent(String path, Component c) {
		if(path.isEmpty()) {
			throw new IllegalArgumentException("Can't override the top-layer component");
		}
		if(path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		int i = path.lastIndexOf("/");
		if(i == -1) {
			Object o = map.add(path, c);
			return get(o);
		} else {
			String parent = path.substring(0, i);
			String name = path.substring(i + 1, path.length());
			Directory d = getDirectory(parent.split("/"));
			if(d != null) {
				return get(d.add(name, c));
			} else {
				return null;
			}
		}
	}
	
	//single-char variable names, ACTIVATE
	private class Directory {
		private Container container;
		private Map<String, Object> sub;
		
		Directory(Container c) {
			container = c;
			sub = new HashMap<>();
		}
		
		Object add(String path, Component c) {
			Object o = sub.put(path, c instanceof Container ? new Directory((Container)c) : c);
			if(c != null) container.add(c);
			//yes, I am assuming it's either a Component or a Directory.
			//Maybe if you don't want it to break, you shouldn't have touched it. :u
			if(o != null) container.remove(get(o));
			return o;
		}
	}
	
	private Component get(Object o) {
		return o != null ? (o instanceof Component ? (Component)o : ((Directory)o).container) : null;
	}
}
