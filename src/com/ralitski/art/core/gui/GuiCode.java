package com.ralitski.art.core.gui;

import java.awt.Component;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.ralitski.art.core.ArtCreatorClass;
import com.ralitski.art.core.Controller;
import com.ralitski.art.core.Task;

public class GuiCode implements Task {
	
	private volatile boolean running;
	private GuiFrame frame;
	private Path path;
	
	private Controller controller;
	
	private java.util.List<String> code;
	
	public GuiCode(Controller controller) {
		this.controller = controller;
		code = Collections.synchronizedList(new ArrayList<String>());
	}

	public int getWidth() {
		return 100;
	}

	public int getHeight() {
		return getWidth();
	}
	
	public GuiFrame getFrame() {
		return frame;
	}

	public boolean running() {
		return running;
	}
	
	public void setCode(Collection<Class<?>> code) {
		this.code.clear();
		for(Class<?> c : code) {
			if(ArtCreatorClass.isArtist(c)) this.code.add(c.getName());
		}
		List list = new List(this.code.size());
		for(String s : this.code) {
			list.add(s);
		}
		list.addActionListener(new ListListener());
		path.addComponent("list", list);
	}

	public void stop() {
		running = false;
	}

	@Override
	public void setup() {
		frame = new GuiFrame(this);
		frame.setup();
		
		path = new Path(frame.getFrame());
		
		setCode(controller.getClassLoader().getClasses());
		
		frame.getFrame().pack();
	}
	
	public Component addComponent(String path, Component c) {
		return this.path.addComponent(path, c);
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
	
	private class ListListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String s = e.getActionCommand();
			controller.dispatchCommand("draw " + s);
		}
	}

}
