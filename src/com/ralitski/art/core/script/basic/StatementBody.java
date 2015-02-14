package com.ralitski.art.core.script.basic;

import java.util.ArrayList;
import java.util.List;

import com.ralitski.art.api.Util;
import com.ralitski.art.core.script.AbstractStatement;
import com.ralitski.art.core.script.Program;
import com.ralitski.art.core.script.Statement;

public class StatementBody extends AbstractStatement {
	
	private List<Statement> children = new ArrayList<>();
	private Statement current;
	private boolean running = false;

	@Override
	public String getPlaintext() {
		String[] parts = new String[children.size()];
		for(int i = 0; i < parts.length; i++) {
			parts[i] = children.get(i).getPlaintext();
		}
		return Util.combine(parts, '\n');
	}

	@Override
	public Statement[] getChildren() {
		return children.toArray(new Statement[children.size()]);
	}
	
	public void addChild(Statement s) {
		children.add(s);
	}

	@Override
	public void execute(Program program) {
		running = true;
		for(Statement s : children) {
			if(!running) break;
			current = s;
			s.execute(program);
		}
	}

	@Override
	public void stop() {
		running = false;
		if(current != null) current.stop();
	}

}
