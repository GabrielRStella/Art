package com.ralitski.art.core.script;

public class Program {
	
	private Statement body;
	private ProgramData data;
	
	public Program(Statement body, ProgramData data) {
		this.body = body;
		this.data = data;
	}
	
	public Statement getBody() {
		return body;
	}
	
	public ProgramData getData() {
		return data;
	}
	
	public void execute() {
		body.execute(this);
	}
	
	//called by an END tag to stop the program (or errors could stop it, I guess)
	public void stop() {
		body.stop();
	}
}
