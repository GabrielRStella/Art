package com.ralitski.art.core.script;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
	
	public void addObject(String name, Map<String, Class<?>> data) {
		addObject(name, new ValueList(data));
	}
	
	public void addObject(String name, ValueList values) {
		objects.put(name, values);
	}
	
	public ValueList deleteObject(String name) {
		return objects.remove(name);
	}
}
