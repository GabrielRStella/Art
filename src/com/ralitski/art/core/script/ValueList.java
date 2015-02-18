package com.ralitski.art.core.script;

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
	
	public boolean has(String s) {
		return values.containsKey(s);
	}
	
	public Class<?> getTypeOf(String key) {
		Value v = values.get(key);
		return v != null ? v.type : null;
	}
    
    public Object get(String field) {
    	Value v = values.get(field);
    	return v != null ? v.value : null;
    }
    
    public byte getByte(String field) {
    	Object o = get(field);
    	return o != null ? (o instanceof Number ? ((Number)o).byteValue() : 0) : 0;
    }
    
    public short getShort(String field) {
    	Object o = get(field);
    	return o != null ? (o instanceof Number ? ((Number)o).shortValue() : 0) : 0;
    }
    
    public int getInt(String field) {
    	Object o = get(field);
    	return o != null ? (o instanceof Number ? ((Number)o).intValue() : 0) : 0;
    }
    
    public long getLong(String field) {
    	Object o = get(field);
    	return o != null ? (o instanceof Number ? ((Number)o).longValue() : 0) : 0;
    }
    
    public float getFloat(String field) {
    	Object o = get(field);
    	return o != null ? (o instanceof Number ? ((Number)o).floatValue() : 0) : 0;
    }
    
    public double getDouble(String field) {
    	Object o = get(field);
    	return o != null ? (o instanceof Number ? ((Number)o).doubleValue() : 0) : 0;
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
    
    public byte setByte(String field, byte value) {
    	Object o = set(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).byteValue() : 0) : 0;
    }
    
    public short setShort(String field, short value) {
    	Object o = set(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).shortValue() : 0) : 0;
    }
    
    public int setInt(String field, int value) {
    	Object o = set(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).intValue() : 0) : 0;
    }
    
    public long setLong(String field, long value) {
    	Object o = set(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).longValue() : 0) : 0;
    }
    
    public float setFloat(String field, float value) {
    	Object o = set(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).floatValue() : 0) : 0;
    }
    
    public double setDouble(String field, double value) {
    	Object o = set(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).doubleValue() : 0) : 0;
    }
    
    //sets or adds a new field
    public Object add(String field, Object value) {
    	Value v = values.get(field);
    	if(v == null) {
    		values.put(field, v = new Value(value.getClass()));
    		v.value = value;
        	return null;
    	} else {
    		Object prevValue = v.value;
    		v.value = value;
    		return prevValue;
    	}
    }
    
    public byte addByte(String field, byte value) {
    	Object o = add(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).byteValue() : 0) : 0;
    }
    
    public short addShort(String field, short value) {
    	Object o = add(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).shortValue() : 0) : 0;
    }
    
    public int addInt(String field, int value) {
    	Object o = add(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).intValue() : 0) : 0;
    }
    
    public long addLong(String field, long value) {
    	Object o = add(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).longValue() : 0) : 0;
    }
    
    public float addFloat(String field, float value) {
    	Object o = add(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).floatValue() : 0) : 0;
    }
    
    public double addDouble(String field, double value) {
    	Object o = add(field, value);
    	return o != null ? (o instanceof Number ? ((Number)o).doubleValue() : 0) : 0;
    }
    
    private class Value {
    	private Class<?> type;
    	private Object value;
    	
    	Value(Class<?> type) {
    		this.type = type;
    	}
    }
}
