package com.ralitski.art.core;


import java.lang.reflect.Field;
import sun.misc.Unsafe;

@SuppressWarnings("restriction")
public class UnsafeKey {
	
	private static Field theUnsafe;
	private static Unsafe unsafe;
//	private static UnsafeMirror unsafeMirror;
	
	public static Unsafe getUnsafe() {
		if (unsafe == null) {
			if(theUnsafe == null) {
				try {
					Class<?> c = Class.forName("sun.misc.Unsafe");
					theUnsafe = c.getDeclaredField("theUnsafe");
					theUnsafe.setAccessible(true);
					unsafe = (Unsafe)theUnsafe.get(null);
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return unsafe;
	}
}
