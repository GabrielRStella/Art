package com.ralitski.art.core.script;

import com.ralitski.art.api.Util;

public class ScriptReader {
	
	private StatementReader reader;
	
	public ScriptReader(StatementReader reader) {
		this.reader = reader;
	}

	public StatementReader getReader() {
		return reader;
	}

	public void setReader(StatementReader reader) {
		this.reader = reader;
	}
	
	public Statement read(String[] sArray) {
		return read(Util.combine(sArray, '\n'));
	}
	
	public Statement read(String s) {
		StringInputStream in = new StringInputStream(s);
		return reader.readAll(in);
	}
}
