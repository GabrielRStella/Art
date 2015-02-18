package com.ralitski.art.core.script;

public interface StatementReader {
	//load full program
	Statement readAll(StringInputStream in, ProgramData data);
	//worker methods
	Statement readOne(StringInputStream in, ProgramData data);
}
