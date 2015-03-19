package com.ralitski.art.api;

import java.awt.Color;

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
	
	public static String combine(String[] sArray, String combine) {
		char[] add = combine.toCharArray();
		int len = 0;
		for(String s : sArray) {
			len += s.length();
		}
		len += (sArray.length - 1) * add.length;
		char[] data = new char[len];
		int index = 0;
		for(String s : sArray) {
			char[] c = s.toCharArray();
			len = c.length;
			System.arraycopy(c, 0, data, index, len);
			index += len;
			if(index < data.length) {
				len = add.length;
				System.arraycopy(add, 0, data, index, len);
				index += len;
			}
		}
		return new String(data);
	}
	
	public static Color getColorWithAlpha(Color color, float alpha) {
		float cr = (float)color.getRed() / 255F;
		float cg = (float)color.getGreen() / 255F;
		float cb = (float)color.getBlue() / 255F;
		return new Color(cr, cg, cb, alpha);
	}

}
