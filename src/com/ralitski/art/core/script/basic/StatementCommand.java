package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.AbstractStatement;
import com.ralitski.art.core.script.Program;

public class StatementCommand extends AbstractStatement {
	
	private CommandExecutor command;

	public StatementCommand(CommandExecutor cmd) {
		this.command = cmd;
	}

	@Override
	public void execute(Program program) {
		command.execute(program);
	}

	@Override
	public String getPlaintext() {
		return command.getPlainText();
	}

	@Override
	public void stop() {
		//no way this can be stopped
	}
	
}
