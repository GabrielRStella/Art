package com.ralitski.art.core.script.basic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.ralitski.art.api.Pair;
import com.ralitski.art.core.script.AbstractStatement;
import com.ralitski.art.core.script.Program;
import com.ralitski.art.core.script.Statement;

public class StatementIF extends AbstractStatement {
	
	private List<Pair<Condition, Statement>> children = new LinkedList<>();
	private Statement running;
	
	public StatementIF addChild(Condition c, Statement s) {
		children.add(new Pair<>(c, s));
		return this;
	}
	
	@Override
	public String getPlaintext() {
		Iterator<Pair<Condition, Statement>> iter = children.iterator();
		String total = "";
		while(iter.hasNext()) {
			Pair<Condition, Statement> p = iter.next();
			Condition condition = p.getKey();
			Statement child = p.getValue();
			String c = child.getPlaintext();
			boolean multi = c.contains("\n");
			String s = "IF " + condition.getPlainText() + " THEN " + (multi ? "\n" + c : c);
			if(iter.hasNext()) s += "\nELSE";
			else if(children.size() == 1 && !multi) {
				return s;
			}
			total = total + s;
		}
		return total;
	}

	@Override
	public void execute(Program program) {
		for(Pair<Condition, Statement> p : children) {
			Condition c = p.getKey();
			Statement s = p.getValue();
			if(c.evaluate(program)) {
				running = s;
				s.execute(program);
				break;
			}
		}
		running = null;
	}

	@Override
	public Statement[] getChildren() {
		Statement[] s = new Statement[children.size()];
		int i = 0;
		for(Pair<Condition, Statement> p : children) {
			s[i++] = p.getValue();
		}
		return s;
	}

	@Override
	public void stop() {
		if(running != null) running.stop();
		running = null;
	}

}
