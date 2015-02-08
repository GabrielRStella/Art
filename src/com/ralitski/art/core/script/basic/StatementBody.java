package com.ralitski.art.core.script.basic;

import java.util.ArrayList;
import java.util.List;

import com.ralitski.art.api.Util;
import com.ralitski.art.core.script.AbstractStatement;
import com.ralitski.art.core.script.Statement;

public class StatementBody extends AbstractStatement {
	
	private List<Statement> children = new ArrayList<>();

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

}
