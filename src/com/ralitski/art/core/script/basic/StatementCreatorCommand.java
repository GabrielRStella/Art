package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.Statement;
import com.ralitski.art.core.script.StatementCreator;
import com.ralitski.art.core.script.StatementReader;
import com.ralitski.art.core.script.StringInputStream;

public class StatementCreatorCommand implements StatementCreator {

	@Override
	public Statement read(StringInputStream in, StatementReader reader) {
		String cmd = in.read(' ');
		//hmmm... TODO
		return null;
	}

	@Override
	public boolean identify(StringInputStream in) {
		// TODO Auto-generated method stub
		return false;
	}

}
