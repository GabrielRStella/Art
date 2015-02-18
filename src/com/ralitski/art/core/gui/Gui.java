package com.ralitski.art.core.gui;

import java.awt.Button;
import java.awt.Component;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;

import com.ralitski.art.core.ArtCreatorClass;
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

		Button btnExtract = new Button("Extract Classes");
		btnExtract.addActionListener(new ListenerExtractClasses());
		addComponent("main/extractClasses", btnExtract);

		Button btnLoadClasses = new Button("Load Classes");
		btnLoadClasses.addActionListener(new ListenerLoadClasses());
		addComponent("main/loadClasses", btnLoadClasses);

		Button btnExtractScripts = new Button("Extract Scripts");
		btnExtractScripts.addActionListener(new ListenerExtractScripts());
		addComponent("main/extractScripts", btnExtractScripts);

		Button btnLoadScripts = new Button("Load Scripts");
		btnLoadScripts.addActionListener(new ListenerLoadScripts());
		addComponent("main/loadScripts", btnLoadScripts);

		Button btnLoadSettings = new Button("Reload Settings");
		btnLoadSettings.addActionListener(new ListenerLoadSettings());
		addComponent("main/reloadSettings", btnLoadSettings);
		
		Button btnSaveSettings = new Button("Save Settings");
		btnSaveSettings.addActionListener(new ListenerSaveSettings());
		addComponent("main/saveSettings", btnSaveSettings);
		
		addComponent("art", new Panel());
		addComponent("scripts", new Panel());
	}
	
	/*
	 * 
	 */
	
	//action listeners
	
	private class ListenerExtractClasses implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Gui.this.controller.getClassExtractor().extractJar();
		}
	}
	
	private class ListenerLoadClasses implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			List<Class<?>> classes = Gui.this.controller.getClassLoader().loadClasses();
			if(classes != null && !classes.isEmpty()) {
				java.awt.List list = new java.awt.List(classes.size());
				for(Class<?> c : classes) {
					if(ArtCreatorClass.isArtist(c)) {
						list.add(c.getName());
						System.out.println("Added class: " + c.getName());
					} else {
						System.out.println("Skipped class: " + c.getName());
					}
				}
				list.addActionListener(new ListenerArtList());

				addComponent("art/label", new Label("Art:"));
				addComponent("art/list", list);
			} else {
				addComponent("art/label", null);
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
	
	private class ListenerSaveSettings implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Gui.this.controller.getSettings().save();
		}
		
	}
	
	private class ListenerExtractScripts implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(1);
			Gui.this.controller.getScriptExtractor().extractJar();
		}
	}
	
	private class ListenerLoadScripts implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			List<String> scripts = Gui.this.controller.getScriptLoader().loadScripts();
			if(scripts != null && !scripts.isEmpty()) {
				java.awt.List list = new java.awt.List(scripts.size());
				for(String script : scripts) {
					list.add(script);
					System.out.println("Added script: " + script);
				}
				list.addActionListener(new ListenerScriptList());

				addComponent("scripts/label", new Label("Scripts:"));
				addComponent("scripts/list", list);
			} else {
				addComponent("scripts/label", null);
				addComponent("scripts/list", null);
			}
			refresh();
		}
	}
	
	private class ListenerScriptList implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			if(s != null) {
				Controller controller = Gui.this.controller;
				String script = controller.getScriptLoader().getScript(s);
				ArtManager.createArt(controller, s, script);
			}
		}
	}

}
