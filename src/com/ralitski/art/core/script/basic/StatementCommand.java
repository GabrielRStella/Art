package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.AbstractStatement;
import com.ralitski.art.core.script.Program;
import com.ralitski.art.core.script.ValueList;

public class StatementCommand extends AbstractStatement {
	
	private String command;
	private String arguments;

	@Override
	public void execute(Program program) {
		ValueList commands = program.getData().getObject("$COMMANDS");
		Object command = commands.get(this.command);
		if(command instanceof Command) {
			Command c = (Command)command;
			c.execute(program, arguments);
		}
	}

	@Override
	public String getPlaintext() {
		return command + " " + arguments;
	}

	@Override
	public void stop() {
		//no way this can be stopped
	}
	
}
