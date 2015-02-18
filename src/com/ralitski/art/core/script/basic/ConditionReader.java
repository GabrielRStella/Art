package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.ProgramData;
import com.ralitski.art.core.script.StatementReader;
import com.ralitski.art.core.script.StringInputStream;

public class ConditionReader {
	
	public static ConditionReader reader = new ConditionReader();
	
	private ConditionReader(){}

	public Condition read(StringInputStream in, StatementReader reader, ProgramData data) {
	}

	public boolean identify(StringInputStream in, ProgramData data) {
	}

}