package com.ralitski.art.core.cmd;

import com.ralitski.art.core.Controller;

public class CommandHandler {
	
	private Controller controller;
	
	public CommandHandler(Controller controller) {
		this.controller = controller;
	}

	public void handle(String cmd) {
	}
}
