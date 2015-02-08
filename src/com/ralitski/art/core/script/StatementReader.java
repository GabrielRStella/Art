package com.ralitski.art.core.script;

public interface StatementReader {
	//load full program
	Statement readAll(StringInputStream in);
	//worker methods
	Statement readOne(StringInputStream in);
}
