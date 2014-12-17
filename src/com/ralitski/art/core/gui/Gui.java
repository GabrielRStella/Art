package com.ralitski.art.core.gui;

import java.awt.Button;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;

import com.ralitski.art.core.ArtManager;
import com.ralitski.art.core.ArtistBank;
import com.ralitski.art.core.CodeBank;
import com.ralitski.art.core.Extractor;
import com.ralitski.art.core.Settings;

public class Gui {
	
	private volatile boolean running;
	private GuiFrame frame;
	private Path path;
	
	public Gui() {
		
	}

	public int getWidth() {
		return 100;
	}

	public int getHeight() {
		return getWidth();
	}

	public boolean running() {
		return running;
	}

	public void stop() {
		running = false;
	}
	
	public void setup() {
		frame = new GuiFrame(this);
		frame.setup();
		
		path = new Path(frame.getFrame());
		
		//add buttons and stuff
		
		addComponents();
		
		refresh();
	}

	public void start() {
		if(running) {
			throw new IllegalStateException("Gui is already running");
		} else {
			running = true;
			while(running && frame.running()) {
				//loop
			}
			frame.stop();
			running = false;
			frame = null;
		}
	}
	
	public Component addComponent(String path, Component c) {
		return this.path.addComponent(path, c);
	}
	
	//must be called after adding components
	public void refresh() {
		frame.getFrame().pack();
	}
	
	private void addComponents() {

		Panel pMain = new Panel();
		pMain.setLayout(new BoxLayout(pMain, BoxLayout.Y_AXIS));
		addComponent("main", pMain);

		Button btnExtract = new Button("Extract");
		btnExtract.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Extractor.extractJar();
				} catch (IOException i) {
					System.err.println("Unable to extract artists from jar");
					i.printStackTrace();
				}
			}});
		addComponent("main/extract", btnExtract);
		
		Button btnLoad = new Button("Load Classes");
		btnLoad.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				File f = new File(Settings.CODE_PATH);
				if(f.exists() && f.isDirectory()) {
					CodeBank.loadClasses(f);
					ArtistBank.refresh();
					loadArtPanel();
				} else {
					f.mkdirs();
					System.out.println("Code directory created.");
				}
			}});
		addComponent("main/load", btnLoad);

		loadArtPanel();
		
	}
	
	private void loadArtPanel() {
		Map<String, ArtManager> art = ArtistBank.getArtists();
		synchronized(art) {
			if(!art.isEmpty()) {
				Panel pArt = new Panel();
				addComponent("art", pArt);
				java.awt.List list = new java.awt.List(art.size(), false);
				for(String s : art.keySet()) {
					list.add(s);
				}
				list.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						String name = e.getActionCommand();
						final ArtManager manager = ArtistBank.getArt(name);
						//switch out a new one
						ArtManager replace = manager.clone();
						ArtistBank.addArt(replace);
						//make the art
						System.out.println("Loading art: " + manager.getName());
						manager.setup();
						System.out.println("Loaded art: " + manager.getName());
						Thread t = new Thread(new Runnable() {
							@Override
							public void run() {
								manager.start();
							}
						});
						t.start();
					}
					
				});
				addComponent("art/list", list);
				refresh();
			}
		}
	}
	
	/*
	 * 
	 */
	
	//action listeners

}
