package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.Program;

public interface Command {
	void execute(Program program, String arguments);
}
