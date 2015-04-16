package com.ralitski.art.core.cmd;

public class CommandNotFoundException extends RuntimeException {

	/**
	 * @author "eclipse"
	 */
	private static final long serialVersionUID = -1008301887532064794L;
	
	public CommandNotFoundException(String command) {
		super("Command \"" + command + "\" not recognized");
	}

}
