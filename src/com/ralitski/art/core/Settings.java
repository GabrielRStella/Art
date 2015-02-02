package com.ralitski.art.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.CollationKey;
import java.text.Collator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Settings {
	
	private static final String SEPARATOR = ": ";
	private static final String PATH = "./settings.txt";
	
	private Map<String, String> values;
	
	public Settings() {
		values = new HashMap<>();
	}
	
	public Settings getSubSettings(String path) {
		return new SubSettings(this, path);
	}
	
	public String get(String key) {
		synchronized(values) {
			return values.get(key);
		}
	}
	
	public String get(String key, String defaultValue) {
		synchronized(values) {
			String s = values.get(key);
			if(s == null) {
				values.put(key, s = defaultValue);
			}
			return s;
		}
	}
	
	public String set(String key, String value) {
		synchronized(values) {
			return values.put(key, value);
		}
	}
	
	public int getInt(String key) {
		try {
			return Integer.parseInt(get(key));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int getInt(String key, int defaultValue) {
		String raw = get(key, ""+defaultValue);
		try {
			return Integer.parseInt(raw);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			set(key, ""+defaultValue);
			return defaultValue;
		}
	}
	
	public int setInt(String key, int value) {
		try {
			return Integer.parseInt(set(key, ""+value));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public <T extends Enum<?>> T getEnum(String key, Class<T> eClass) {
		String s = get(key);
		T t = valueOf(eClass, s);
		return t;
	}
	
	public <T extends Enum<?>> T getEnum(String key, T defaultValue, Class<T> eClass) {
		T t = getEnum(key, eClass);
		if(t == null) {
			t = defaultValue;
			if(t != null) set(key, t.name());
			else set(key, null);
		}
		return t;
	}
	
	public <T extends Enum<?>> T setEnum(String key, T value, Class<T> eClass) {
		T t = getEnum(key, eClass);
		if(value != null) set(key, value.name());
		else set(key, null);
		return t;
	}
	
	public void save() {
		try {
			save(PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(String f) throws IOException {
		save(new File(f));
	}
	
	public void save(File f) throws IOException {
		if(!f.exists()) {
			f.createNewFile();
		}
		
		//alphabetical sort
		Collator c = Collator.getInstance();
		Map<CollationKey, String> sortMap = new TreeMap<>();
		synchronized(values) {
			for(Entry<String, String> e : values.entrySet()) {
				sortMap.put(c.getCollationKey(e.getKey()), e.getValue());
			}
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		for(Entry<CollationKey, String> e : sortMap.entrySet()) {
			writer.write(e.getKey().getSourceString() + SEPARATOR + e.getValue());
			writer.newLine();
		}
		
		writer.close();
	}
	
	public void load() {
		try {
			load(PATH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void load(String f) throws IOException {
		load(new File(f));
	}
	
	public void load(File f) throws IOException {
		if(!f.exists()) {
			f.createNewFile();
			return;
		}
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line;
		synchronized(values) {
			while((line = reader.readLine()) != null) {
				String[] data = line.split(SEPARATOR, 2);
				if(data.length == 2) {
					values.put(data[0], data[1]);
				}
			}
		}
		reader.close();
	}
	
	//utility method from my ArrayUtils
    
    @SuppressWarnings("unchecked")
	private static <T extends Enum<?>> T valueOf(Class<T> clazz, String value) {
        try {
            Method m = clazz.getDeclaredMethod("valueOf", String.class);
            Object o = m.invoke(null, value);
            return (T)o;
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        return null;
    }
}
