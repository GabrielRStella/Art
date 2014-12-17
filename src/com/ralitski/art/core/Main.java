package com.ralitski.art.core;

import java.io.File;
import java.io.IOException;

public class Main {
	
	public static void main(String[] args) {
		File f = new File("./code/");
		try {
			Extractor.extract("./art.jar", "com/ralitski/art/test", f.getPath());
		} catch (IOException e) {
			System.err.println("Unable to extract artists from jar");
			e.printStackTrace();
		}
		if(f.exists() && f.isDirectory()) {
			for(Class<?> c : ClassLoader.loadClasses(f)) {
				createArt(c);
			}
		} else {
			f.mkdirs();
			System.out.println("Code directory created.");
		}
	}
	
	public static void createArt(Class<?> c) {
		try {
			final ArtManager manager = new ArtManager(c);
			if(manager.getArtist() != null) {
				System.out.println("Loading art: " + manager.getName());
				manager.setup();
				System.out.println("Loaded art: " + manager.getName());
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						manager.start();
					}
				});
				t.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
