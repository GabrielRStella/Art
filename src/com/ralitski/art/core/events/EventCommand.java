package com.ralitski.art.core.events;

public class EventCommand {
	
	private String command;
	private boolean pre;
	
	public EventCommand(String command, boolean pre) {
		this.command = command;
		this.pre = pre;
	}
	
	public String getCommand() {
		return command;
	}

	public boolean isPreExecution() {
		return pre;
	}
}
