package com.ralitski.art.core;

public class Main {
	
	/*
	 * TODO:
	 * -add scripting
	 * -clean up voronoi
	 * -add log + console to gui, use gui commands instead of buttons
	 * -add settings to gui
	 * -detect IDE setup (no jar) and change files layout
	 * -add PrintStream wrapped around console log to output errors and whatnot
	 */
	
	public static void main(String[] args) {
		Controller c = new Controller();
		c.setup();
		c.start();
	}
}
