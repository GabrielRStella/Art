package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.AbstractStatement;
import com.ralitski.art.core.script.Program;
import com.ralitski.art.core.script.Statement;

public class StatementIF extends AbstractStatement {
	
	private Condition condition;
	private Statement child;
	private boolean running;
	
	@Override
	public String getPlaintext() {
		//TODO
		return null;
	}

	@Override
	public void execute(Program program) {
		if(condition.evaluate(program)) {
			running = true;
			child.execute(program);
		}
		running = false;
	}

	@Override
	public Statement[] getChildren() {
		//TODO
		return null;
	}

	@Override
	public void stop() {
		if(running) child.stop();
		running = false;
	}

}
