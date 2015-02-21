package com.ralitski.art.core.script.basic.var;

import com.ralitski.art.core.script.ValueList;

/**
 *
 * @author ralitski
 */
public class VariableReference implements Variable {
    
	private String name;
    
    public VariableReference(String name) {
    	this.name = name;
    }
    
    @Override
    public float get(ValueList obj) {
    	return obj.getFloat(name);
    }
    
    public String toString() {
    	return name;
    }
}
