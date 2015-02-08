package com.ralitski.art.core.script;

public abstract class AbstractStatement implements Statement {
	
	private Statement parent;

	@Override
	public Statement getParent() {
		return parent;
	}

	@Override
	public void setParent(Statement statement) {
		this.parent = statement;
	}

	@Override
	public Statement[] getChildren() {
		return null;
	}

}
