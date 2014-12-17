package com.ralitski.art.core;

import com.ralitski.art.core.gui.Gui;

public class Main {
	
	/*
	 * TODO:
	 * -create a ClassLoader that can drop old artists
	 */
	
	public static void main(String[] args) {
		Gui g = new Gui();
		g.setup();
		g.start();
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
