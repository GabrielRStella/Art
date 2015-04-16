package com.ralitski.art.core;

public class Main {
	
	/*
	 * TODO:
	 * -add scripting
	 * -clean up voronoi
	 * -add better settings to gui
	 * -find way to detect jar name...nothing works ;-;
	 * -event hooks (PRE and POST command dispatch)
	 */
	
	public static void main(String[] args) {
		Controller c = new Controller();
		c.setup();
		c.start();
	}
}
