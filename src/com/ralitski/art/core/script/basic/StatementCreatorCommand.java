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
			return new StatementCommand(cmd, args);
		}
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
