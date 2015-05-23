package com.ralitski.art.core;

public class Main {
	
	/*
	 * TODO:
	 * -add scripting
	 * -add better settings to gui
	 * -find way to detect jar name...nothing works ;-;
	 */
	
	public static void main(String[] args) {
		Controller c = new Controller();
		c.setup();
		c.start();
	}
}
