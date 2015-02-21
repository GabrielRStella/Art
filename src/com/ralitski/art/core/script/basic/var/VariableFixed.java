package com.ralitski.art.core.script.basic.var;

import com.ralitski.art.core.script.ValueList;

/**
 *
 * @author ralitski
 */
public class VariableFixed implements Variable {
    
    private float value;
    
    public VariableFixed(float value) {
        this.value = value;
    }

    @Override
    public float get(ValueList obj) {
        return value;
    }
    
    public String toString() {
    	return ""+value;
    }

}
