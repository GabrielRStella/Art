package com.ralitski.art.core.gui;

import java.awt.Component;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;

import com.ralitski.art.core.Controller;
import com.ralitski.art.core.Settings;
import com.ralitski.art.core.Task;

public class GuiConsole implements Task {
	
	private volatile boolean running;
	private GuiFrame frame;
	private Path path;
	
	private Controller controller;
	
	private TextArea log;
	private TextField in;
	
	public GuiConsole(Controller controller) {
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

	public void stop() {
		running = false;
		controller.stop();
	}

	@Override
	public void setup() {
		throw new UnsupportedOperationException("Gui requires Settings to setup.");
	}
	
	public void setup(Settings settings) {
		frame = new GuiFrame(this);
		frame.setup();
		
		path = new Path(frame.getFrame());
		
		int width = settings.getInt("guiWidth", 80);
		int height = settings.getInt("guiHeight", 20);
		
		//add console
		Panel pConsole = new Panel();
		addComponent("console", pConsole);
		pConsole.setLayout(new BoxLayout(pConsole, BoxLayout.Y_AXIS));
		log = new TextArea(height, width);
		log.setEditable(false);
		addComponent("console/log", log);
		in = new TextField(width);
		in.addActionListener(new ConsoleInputListener());
		addComponent("console/in", in);

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
			controller.stop();
		}
	}
	
	public void log(String text) {
		log.append(text);
	}
	
	private class ConsoleInputListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			controller.dispatchCommand(e.getActionCommand());
			in.setText("\n");
		}
	}

}
