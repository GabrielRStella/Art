package com.ralitski.art.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ExtractorFile implements Runnable {
	
	private String sourceDir;
	private String destDir;
	private String fileType;
	
	public ExtractorFile(String sourceDir, String destDir, String fileType) {
		this.sourceDir = new File(sourceDir).getAbsolutePath();
		this.destDir = new File(destDir).getAbsolutePath();
		this.fileType = fileType;
	}

	@Override
	public void run() {
		try {
			extract();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void extract() throws IOException {
		File f = new File(sourceDir);
		scan(f);
	}
	
	private void scan(File f) throws IOException {
		if(f.isDirectory()) {
			for(File f2 : f.listFiles()) {
				scan(f2);
			}
		} else if(fileType == null || f.getName().endsWith(fileType)) {
			copyFile(f);
		}
	}
	
	private void copyFile(File f) throws IOException {
		String s = f.getAbsolutePath();
		s = s.substring(sourceDir.length());
		String dst = destDir + s;
		copyFile(f, new File(dst));
	}
	
	private void copyFile(File src, File dest) throws IOException {
		dest.getParentFile().mkdirs();
		dest.createNewFile();
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dest);
		while(in.available() > 0) {
			byte[] b = new byte[in.available()];
			int len = in.read(b);
			out.write(b, 0, len);
			out.flush();
		}
		in.close();
		out.close();
	}

}
