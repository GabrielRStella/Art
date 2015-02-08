package com.ralitski.art.core.script;

public interface StatementCreator {
	Statement read(StringInputStream in, StatementReader reader);
	//should read the least possible length necessary to identify itself
	//not sure if necessary; creator can return null in read()...
	boolean identify(StringInputStream in);
}
