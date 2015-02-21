package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.Program;

public interface CommandExecutor {
	void execute(Program program);
	String getPlainText();
}
