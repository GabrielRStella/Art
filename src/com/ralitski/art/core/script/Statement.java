package com.ralitski.art.core.script;

public interface Statement {
	String getPlaintext();
	void execute(Program program);
	void stop();
	
	Statement getParent();
	void setParent(Statement statement);
	//return null if not a compound statement
	Statement[] getChildren();
}
