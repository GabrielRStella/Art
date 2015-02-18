package com.ralitski.art.core.script.basic;

import java.util.LinkedList;
import java.util.List;

import com.ralitski.art.core.script.ProgramData;
import com.ralitski.art.core.script.Statement;
import com.ralitski.art.core.script.StatementCreator;
import com.ralitski.art.core.script.StatementReader;
import com.ralitski.art.core.script.StringInputStream;

public class BasicReader implements StatementReader {
	
	/*
	 * TODO:
	 * label:
	 * GOTO
	 * IF/ELSE/ELSEIF
	 * variables
	 * arrays
	 * subroutine: RETURN
	 * loops
	 * 
	 * sidenote: totally not gonna deal with strings, nope. also $ is reserved, skrub
	 */
	
	//a map of the types of statements
	private List<StatementCreator> readers;
	
	public BasicReader() {
		readers = new LinkedList<>();
		readers.add(new StatementCreatorCommand());
		readers.add(new StatementCreatorEND());
		readers.add(new StatementCreatorIF());
	}

	@Override
	public Statement readAll(StringInputStream in, ProgramData data) {
		StatementBody body = new StatementBody();
		while(in.available() > 0) {
			Statement s = readOne(in, data);
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
	public Statement readOne(StringInputStream in, ProgramData data) {
		for(StatementCreator creator : readers) {
			int avail = in.available();
			boolean flag = creator.identify(in, data);
			//restore previous position
			avail -= in.available();
			in.back(avail);
			if(flag) {
				return creator.read(in, this, data);
			}
		}
		return null;
	}

}
