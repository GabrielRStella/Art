package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.Statement;
import com.ralitski.art.core.script.StatementCreator;
import com.ralitski.art.core.script.StatementReader;
import com.ralitski.art.core.script.StringInputStream;

public class StatementCreatorEND implements StatementCreator {

	@Override
	public Statement read(StringInputStream in, StatementReader reader) {
		String s = in.readUntil('E', 'D');
		if(s.equals("END")) {
			return new StatementEND();
		} else return null;
	}

	@Override
	public boolean identify(StringInputStream in) {
		return false;
	}

}
