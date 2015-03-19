package com.ralitski.art.core.cmd;

public @interface CommandMark {
	String name();
	String[] aliases();
	String usage();
	String[] help();
}
