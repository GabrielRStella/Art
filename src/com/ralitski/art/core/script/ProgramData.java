package com.ralitski.art.core.script;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

//TODO: scopes; maybe handle like SubSettings
//TODO: system values
public class ProgramData {
	
	private Map<String, ValueList> objects = new HashMap<>();
	
	public ProgramData() {
		
	}
	
	public Set<String> getObjects() {
		return objects.keySet();
	}
	
	public ValueList getObject(String name) {
		return objects.get(name);
	}
	
	public ValueList addObject(String name) {
		return addObject(name, new ValueList());
	}
	
	public ValueList addObject(String name, Map<String, Class<?>> data) {
		return addObject(name, new ValueList(data));
	}
	
	public ValueList addObject(String name, ValueList values) {
		objects.put(name, values);
		return values;
	}
	
	public ValueList deleteObject(String name) {
		return objects.remove(name);
	}
}
