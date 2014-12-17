package com.ralitski.art.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Extractor {
	
	public static void extract(String jar, String src, String dest) throws IOException {
//		src = src.replaceAll("\\.", "/");
		JarFile file = new JarFile(jar);
		Enumeration<JarEntry> entries = file.entries();
		while(entries.hasMoreElements()) {
			JarEntry e = entries.nextElement();
			if(e.getName().startsWith(src) && e.getName().endsWith(".class")) {
				//extract
				InputStream in = file.getInputStream(e);
				File f = new File(dest + "/" + e.getName());
				f.getParentFile().mkdirs();
//				f.delete();
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				while(in.available() > 0) {
					int i = in.available();
					byte[] b = new byte[i];
					in.read(b);
					out.write(b);
				}
				String name = f.getName();
				name = name.substring(0, name.lastIndexOf("."));
				System.out.println("Extracted artist: " + name);
				in.close();
				out.close();
			}
		}
		file.close();
	}
}
