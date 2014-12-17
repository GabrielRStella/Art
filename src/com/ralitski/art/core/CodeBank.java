package com.ralitski.art.core;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * used to store classes to be used as artists.
 * 
 * @author ralitski
 */
public class CodeBank {
	
	private static List<Class<?>> classes = new LinkedList<>();
	
	public static void loadClasses(File dir) {
		synchronized(classes) {
			for(Class<?> c : ClassLoader.loadClasses(dir)) {
				classes.add(c);
			}
		}
	}
	
	public static void addClass(Class<?> c) {
		synchronized(classes) {
			classes.add(c);
		}
	}
	
	//should synchronize on this list when retrieved
	public static List<Class<?>> getClasses() {
		return classes;
	}
}
