package com.ralitski.art.core.script.basic;

import com.ralitski.art.core.script.ProgramData;

public interface Command {
	String name();
	CommandExecutor init(ProgramData data, String arguments);
}
