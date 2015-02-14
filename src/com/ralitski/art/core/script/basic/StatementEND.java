package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.AbstractStatement;
import com.ralitski.art.core.script.Program;

public class StatementEND extends AbstractStatement {

	@Override
	public void execute(Program program) {
		program.stop();
	}

	@Override
	public String getPlaintext() {
		return "END";
	}

	@Override
	public void stop() {
		//already stopped
	}

}
