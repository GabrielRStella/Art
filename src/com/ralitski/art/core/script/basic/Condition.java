package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.Program;

public interface Condition {
	
	boolean evaluate(Program program);
	
	String getPlainText();
}
