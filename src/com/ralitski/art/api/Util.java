package com.ralitski.art.api;

public class Util {
	
	public static String combine(String[] sArray, char combine) {
		int len = 0;
		for(String s : sArray) {
			len += s.length();
		}
		len += sArray.length - 1;
		char[] data = new char[len];
		int index = 0;
		for(String s : sArray) {
			char[] c = s.toCharArray();
			System.arraycopy(c, 0, data, index, c.length);
			index += s.length();
			if(index < data.length) data[index++] = combine;
		}
		return new String(data);
	}

}
