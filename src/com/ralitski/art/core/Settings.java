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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Settings {
	
	private static final String SEPARATOR = ": ";
	private static final String COMMENT = "#";
	private static final String PATH = "./settings.txt";
	
	private Map<String, Setting> values;
	
	public Settings() {
		values = Collections.synchronizedMap(new HashMap<String, Setting>());
	}
	
	public Settings getSubSettings(String path) {
		return new SubSettings(this, path);
	}
	
	public Set<String> getKeys() {
		return values.keySet();
	}
	
	//detects SubSettings sections
	public Set<String> getSections() {
		Set<String> ret = new HashSet<>();
		for(String s : getKeys()) {
			if(s.contains(".")) {
				ret.add(s.split("\\.")[0]);
			}
		}
		return ret;
	}
	
	public List<String> getComments(String key) {
		Setting s = values.get(key);
		return s != null ? s.comments : null;
	}
	
	/**
	 * 
	 * @param key
	 * @param comment
	 * @return if the key was found and the comment was added (or previously there)
	 */
	public boolean addComment(String key, String comment) {
		Setting s;
		s = values.get(key);
		if(s != null) {
			List<String> c = s.comments;
			if(!c.contains(comment)) c.add(comment);
			return true;
		}
		return false;
	}
	
	public void removeComments(String key) {
		Setting s;
		s = values.get(key);
		if(s != null) {
			s.comments.clear();
		}
	}
	
	public boolean hasSetting(String key) {
		return values.containsKey(key);
	}
	
	public String get(String key) {
		Setting s = values.get(key);
		return s != null ? s.value : null;
	}
	
	public String get(String key, String defaultValue) {
		Setting s = values.get(key);
		String value;
		if(s == null) {
			s = new Setting();
			s.value = value = defaultValue;
			values.put(key, s);
		} else {
			value = s.value;
		}
		return value;
	}
	
	public String set(String key, String value) {
		Setting s = values.get(key);
		if(s == null) values.put(key, s = new Setting());
		String prev = s.value;
		s.value = value;
		return prev;
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
	
	public int getIntRadix(String key, int radix) {
		try {
			return Integer.parseInt(get(key), radix);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public int getIntRadix(String key, String defaultValue, int radix) {
		return getIntRadix(key, Integer.parseInt(defaultValue, radix), radix);
	}
	
	public int getIntRadix(String key, int defaultValue, int radix) {
		String raw = get(key, ""+Integer.toString(defaultValue, radix));
		try {
			return Integer.parseInt(raw, radix);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			set(key, ""+Integer.toString(defaultValue, radix));
			return defaultValue;
		}
	}
	
	public int setIntRadix(String key, int value, int radix) {
		try {
			return Integer.parseInt(set(key, ""+Integer.toString(value, radix)), radix);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public float getFloat(String key) {
		try {
			return Float.parseFloat(get(key));
		} catch (Exception e) {
			e.printStackTrace();
			return Float.NaN;
		}
	}
	
	public float getFloat(String key, float defaultValue) {
		String raw = get(key, ""+defaultValue);
		try {
			return Float.parseFloat(raw);
		} catch (Exception e) {
			e.printStackTrace();
			set(key, ""+defaultValue);
			return defaultValue;
		}
	}
	
	public float setFloat(String key, float value) {
		try {
			return Float.parseFloat(set(key, ""+value));
		} catch (Exception e) {
			e.printStackTrace();
			return Float.NaN;
		}
	}
	
	public <T extends Enum<?>> T getEnum(String key, Class<T> eClass) {
		String s = get(key);
		T t = s == null ? null : valueOf(eClass, s);
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
		Map<CollationKey, Setting> sortMap = new TreeMap<>();
		for(Entry<String, Setting> e : values.entrySet()) {
			sortMap.put(c.getCollationKey(e.getKey()), e.getValue());
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(f));
		for(Entry<CollationKey, Setting> e : sortMap.entrySet()) {
			Setting s = e.getValue();
			for(String comment : s.comments) {
				writer.write(COMMENT + comment);
				writer.newLine();
			}
			writer.write(e.getKey().getSourceString() + SEPARATOR + s.value);
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
		values.clear(); //get rid of old values
		BufferedReader reader = new BufferedReader(new FileReader(f));
		String line;
		Setting current = new Setting();
		while((line = reader.readLine()) != null) {
			if(line.startsWith(COMMENT)) {
				current.comments.add(line.substring(COMMENT.length()));
			} else {
				String[] data = line.split(SEPARATOR, 2);
				if(data.length == 2) {
					String key = data[0];
					String value = data[1];
					current.value = value;
					values.put(key, current);
					current = new Setting();
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
    
    private class Setting {
    	private String value;
    	private List<String> comments = Collections.synchronizedList(new LinkedList<String>());
    }
}
