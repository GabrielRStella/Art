package com.ralitski.art.core.script.basic;

import java.util.List;

import com.ralitski.art.core.script.Statement;
import com.ralitski.art.core.script.StatementCreator;
import com.ralitski.art.core.script.StatementReader;
import com.ralitski.art.core.script.StringInputStream;

public class BasicReader implements StatementReader {
	
	//a map of the types of statements
	private List<StatementCreator> readers;

	@Override
	public Statement readAll(StringInputStream in) {
		StatementBody body = new StatementBody();
		while(in.available() > 0) {
			Statement s = readOne(in);
			if(s != null) {
				body.addChild(s);
			} else {
				//move over one (skip spaces, etc)
				in.skip(1);
			}
		}
		return body;
	}

	@Override
	public Statement readOne(StringInputStream in) {
		for(StatementCreator creator : readers) {
			int avail = in.available();
			boolean flag = creator.identify(in);
			//restore previous position
			avail -= in.available();
			in.back(avail);
			if(flag) {
				return creator.read(in, this);
			}
		}
		return null;
	}

}
