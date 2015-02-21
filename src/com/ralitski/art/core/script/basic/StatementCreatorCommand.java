package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.ProgramData;
import com.ralitski.art.core.script.Statement;
import com.ralitski.art.core.script.StatementCreator;
import com.ralitski.art.core.script.StatementReader;
import com.ralitski.art.core.script.StringInputStream;
import com.ralitski.art.core.script.ValueList;

public class StatementCreatorCommand implements StatementCreator {

	@Override
	public Statement read(StringInputStream in, StatementReader reader, ProgramData data) {
		String cmd = in.read(' ');
		ValueList commands = data.getObject("$COMMANDS");
		Object command = commands.get(cmd);
		if(command instanceof Command) {
			String args = in.read('\n');
			Command c = (Command)command;
			CommandExecutor exec = c.init(data, args);
			return new StatementCommand(exec);
		}
		/*
		ValueList commands = program.getData().getObject("$COMMANDS");
		Object command = commands.get(this.command);
		if(command instanceof Command) {
			Command c = (Command)command;
			c.execute(program, arguments);
		}
		 */
		return null;
	}

	@Override
	public boolean identify(StringInputStream in, ProgramData data) {
		String cmd = in.read(' ');
		ValueList commands = data.getObject("$COMMANDS");
		Object command = commands.get(cmd);
		return command instanceof Command;
	}

}
