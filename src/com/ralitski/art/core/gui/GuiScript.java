package com.ralitski.art.core.gui;

import java.awt.Component;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import com.ralitski.art.core.Controller;
import com.ralitski.art.core.Task;

public class GuiScript implements Task {
	
	private volatile boolean running;
	private GuiFrame frame;
	private Path path;
	
	private Controller controller;
	
	public GuiScript(Controller controller) {
		this.controller = controller;
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
	
	public void setCode(Collection<String> scripts) {
		List list = new List(scripts.size());
		for(String s : scripts) {
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
		
		setCode(controller.getScriptLoader().getScripts());
		
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
