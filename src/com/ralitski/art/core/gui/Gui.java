package com.ralitski.art.core.gui;

import java.awt.Button;
import java.awt.Component;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;

import com.ralitski.art.core.ArtManager;
import com.ralitski.art.core.Controller;

public class Gui {
	
	private volatile boolean running;
	private GuiFrame frame;
	private Path path;
	
	private Controller controller;
	
	public Gui(Controller controller) {
		this.controller = controller;
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
			controller.stop();
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
		addComponent("main/label", new Label("Options:"));

		Button btnExtract = new Button("Extract");
		btnExtract.addActionListener(new ListenerExtractor());
		addComponent("main/extract", btnExtract);

		Button btnLoadClasses = new Button("Load Classes");
		btnLoadClasses.addActionListener(new ListenerLoad());
		addComponent("main/loadClasses", btnLoadClasses);
		
		Button btnLoadSettings = new Button("Reload Settings");
		btnLoadSettings.addActionListener(new ListenerLoadSettings());
		addComponent("main/reloadSettings", btnLoadSettings);

		Panel pArt = new Panel();
		addComponent("art", pArt);
		addComponent("art/label", new Label("Art:"));
	}
	
	/*
	 * 
	 */
	
	//action listeners
	
	private class ListenerExtractor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Gui.this.controller.getExtractor().extractJar();
		}
	}
	
	private class ListenerLoad implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			List<Class<?>> classes = Gui.this.controller.getClassLoader().loadClasses();
			if(classes != null && !classes.isEmpty()) {
				java.awt.List list = new java.awt.List(classes.size());
				for(Class<?> c : classes) {
					if(ArtManager.isArtist(c)) {
						list.add(c.getName());
						System.out.println("Added class: " + c.getName());
					} else {
						System.out.println("Skipped class: " + c.getName());
					}
				}
				list.addActionListener(new ListenerArtList());
				addComponent("art/list", list);
			} else {
				addComponent("art/list", null);
			}
			refresh();
		}
	}
	
	private class ListenerArtList implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			if(s != null) {
				Controller controller = Gui.this.controller;
				Class<?> c = controller.getClassLoader().getClass(s);
				ArtManager.createArt(controller, c);
			}
		}
		
	}
	
	private class ListenerLoadSettings implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Gui.this.controller.getSettings().load();
		}
		
	}

}
