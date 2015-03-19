package com.ralitski.art.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ExtractorJar implements Runnable {
	
	private String jarPath;
	private String pathInJar;
	private String codePath;
	private String fileType;
	
	public ExtractorJar(String jarPath, String pathInJar, String codePath, String fileType) {
		this.jarPath = jarPath;
		this.pathInJar = pathInJar;
		this.codePath = codePath;
		this.fileType = fileType;
	}
	
	public void run() {
		try {
			extractJar(jarPath, pathInJar, codePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void extractJar(String jar, String src, String dest) throws IOException {
		JarFile file = new JarFile(jar);
		Enumeration<JarEntry> entries = file.entries();
		while(entries.hasMoreElements()) {
			JarEntry e = entries.nextElement();
			if(e.getName().startsWith(src) && (fileType == null || e.getName().endsWith(fileType))) {
				//extract
				InputStream in = file.getInputStream(e);
				File f = new File(dest + "/" + e.getName());
				f.getParentFile().mkdirs();
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
				Controller.getInstance().log("Extracted file: " + name);
				in.close();
				out.close();
			}
		}
		file.close();
	}
	
	//currently unnecessary
//	public static void extract(String src, String dest) throws IOException {
//		File file = new File(src);
//		if(file.exists() && file.isDirectory()) {
//			for(File f : file.listFiles()) {
//				if(f.getName().endsWith(".class")) {
//					File fDest = new File(dest + File.separator + f.getName());
//					//copy
//				}
//			}
//		}
//	}
}
