package com.ralitski.art.core.script.exception;

public class ScriptException extends RuntimeException {
	
    /**
	 * thanks eclipse
	 */
	private static final long serialVersionUID = 8801043620775907115L;

	public ScriptException() {
        super();
    }
    
    public ScriptException(String message) {
        super(message);
    }
    
    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ScriptException(Throwable cause) {
        super(cause);
    }

}
