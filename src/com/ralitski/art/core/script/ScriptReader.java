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
	
	public Program read(String[] sArray) {
		return read(Util.combine(sArray, '\n'));
	}
	
	public Program read(String s) {
		StringInputStream in = new StringInputStream(s);
		Statement body = reader.readAll(in);
		return new Program(body);
	}
}
