package com.ralitski.art.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.security.Permissions;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import sun.misc.Unsafe;

@SuppressWarnings("restriction")
public class ClassLoader {
	
	public static Class<?> loadClass(String name, URL url, byte[] code) {
		Unsafe u = UnsafeKey.getUnsafe();
		
		//thanks to: http://stackoverflow.com/questions/27462043/implementing-interfaces-in-dynamic-classes-using-javas-unsafe/27462871#27462871
		java.lang.ClassLoader loader = ClassLoader.class.getClassLoader(); //java.lang.ClassLoader.getSystemClassLoader();
		
		Permissions perms=new Permissions();
		perms.add(new RuntimePermission("accessDeclaredMembers"));
//		perms.add(new RuntimePermission("accessClassInPackage.com.ralitski.art.api"));
//		perms.add(new RuntimePermission("accessClassInPackage.com.ralitski.art.core"));
//		perms.add(new AllPermission());
		ProtectionDomain protection = new ProtectionDomain(new CodeSource(url,(Certificate[])null),perms);
		
		return u.defineClass(name, code, 0, code.length, loader, protection);
	}
	
	public static Class<?> loadClass(String name, URL url, InputStream in) throws IOException {
		int len;
		byte[] code = null;
		while((len = in.available()) > 0) {
			byte[] data = new byte[len];
			code = code != null ? combine(code, data) : data;
		}
		return loadClass(name, url, code);
	}
	
	public static Class<?> loadClass(String name, File f) throws IOException {
		byte[] data = new byte[(int) f.length()];
		InputStream i = new FileInputStream(f);
		i.read(data);
		return loadClass(name, f.toURI().toURL(), data);
	}
	
	public static Class<?> loadClass(File f) throws IOException {
		String name = f.getName();
		int index = name.lastIndexOf(".");
		name = name.substring(0, index);
		return loadClass(name, f);
	}
	
	public static List<Class<?>> loadClasses(File dir) {
		return loadClasses("", dir);
	}
	
	private static List<Class<?>> loadClasses(String prefix, File dir) {
		List<Class<?>> list = new LinkedList<>();
		for(File f2 : dir.listFiles()) {
			if(f2.isDirectory()) {
				list.addAll(loadClasses(prefix + f2.getName() + "/", f2));
			} else if(f2.getName().endsWith(".class")) {
				try {
					String name = f2.getName();
					int index = name.lastIndexOf(".");
					name = name.substring(0, index);
					Class<?> c = loadClass(prefix + name, f2);
					list.add(c);
				} catch (IOException e) {
					System.err.println("Failed to load class from file: " + prefix + f2.getPath());
				}
			}
		}
		return list;
	}
    
    public static byte[] combine(byte[]... originals) {
        int newSize = 0;
        if(originals.length == 0) return new byte[0];
        for(byte[] t : originals) newSize += t.length;
        byte[] newArray = Arrays.copyOf(originals[0], newSize);
        int index = originals[0].length;
        for(int i = 1; i < originals.length; i++) {
            byte[] array = originals[i];
            System.arraycopy(array, 0, newArray, index, array.length);
            index += array.length;
        }
        return newArray;
    }
	
}
