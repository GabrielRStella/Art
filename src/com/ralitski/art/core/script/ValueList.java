package com.ralitski.art.core.script;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ValueList {
	
	private Map<String, Value> values = new HashMap<>();

	public ValueList() {
	}
	
	public ValueList(Map<String, Class<?>> types) {
		for(Entry<String, Class<?>> e : types.entrySet()) {
			values.put(e.getKey(), new Value(e.getValue()));
		}
	}
	
	public Set<String> getFields() {
		return values.keySet();
	}
	
	public boolean addField(String field, Class<?> type) {
		if(values.get(field) == null) {
			values.put(field, new Value(type));
			return true;
		}
		return false;
	}
	
	public Class<?> getTypeOf(String key) {
		Value v = values.get(key);
		return v != null ? v.type : null;
	}
    
    public Object get(String field) {
    	Value v = values.get(field);
    	return v != null ? v.value : null;
    }
    
    //returns previous value if successful
    //will not add a new field
    public Object set(String field, Object value) {
    	Value v = values.get(field);
    	if(v != null) {
    		Object prevValue = v.value;
    		v.value = value;
    		return prevValue;
    	} else {
    		return null;
    	}
    }
    
    //sets or adds a new field
    public Object add(String field, Object value) {
    	Value v = values.get(field);
    	if(v == null) {
    		values.put(field, v = new Value(value.getClass()));
        	return value;
    	} else {
    		Object prevValue = v.value;
    		v.value = value;
    		return prevValue;
    	}
    }
    
    private class Value {
    	private Class<?> type;
    	private Object value;
    	
    	Value(Class<?> type) {
    		this.type = type;
    	}
    }
}
