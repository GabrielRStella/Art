package com.ralitski.art.core;

import java.awt.Frame;
import java.awt.Rectangle;

import com.ralitski.art.core.cmd.CommandMark;
import com.ralitski.art.core.gui.Gui;
import com.ralitski.art.core.gui.GuiCode;
import com.ralitski.art.core.gui.GuiScript;

//https://github.com/XyPhoGR/Art/commit/1430699742d53cefd59b978759085a64878bf7de
public class CmdGui {
	
	@CommandMark(name = "gui",
			aliases = {"g"},
			usage = "[code|script]",
			help = {"Opens a GUI.","The optional \"code\" and \"script\" arguments open GUIs listing the loaded artists."})
	public void onGui(String alias, String args, Controller controller) {
		if(args.equals("code")) {
			openCode(controller);
		} else if(args.equals("script")) {
			openScript(controller);
		} else {
			open(controller);
		}
	}
	
	private void openCode(Controller controller) {
		Thread t = new Thread(new GuiCodeRunnable(controller));
		t.setDaemon(true);
		t.start();
	}
	
	private void openScript(Controller controller) {
		Thread t = new Thread(new GuiScriptRunnable(controller));
		t.setDaemon(true);
		t.start();
	}
	
	private void open(Controller controller) {
		Thread t = new Thread(new GuiRunnable(controller));
		t.setDaemon(true);
		t.start();
	}
	
	private class GuiRunnable implements Runnable {
		
		private Controller controller;
		
		GuiRunnable(Controller controller) {
			this.controller = controller;
		}

		@Override
		public void run() {
			Gui g = new Gui(controller);
			g.setup();
			Frame f = controller.getGui().getFrame().getFrame();
			Rectangle r = f.getBounds();
			g.getFrame().getFrame().setLocation((int)r.getMaxX() + 10, (int)r.getMinY());
			controller.addTask(g);
			g.start();
		}
		
	}
	
	private class GuiCodeRunnable implements Runnable {
		
		private Controller controller;
		
		GuiCodeRunnable(Controller controller) {
			this.controller = controller;
		}

		@Override
		public void run() {
			GuiCode g = new GuiCode(controller);
			g.setup();
			Frame f = controller.getGui().getFrame().getFrame();
			Rectangle r = f.getBounds();
			g.getFrame().getFrame().setLocation((int)r.getMaxX() + 10, (int)r.getMinY());
			controller.addTask(g);
			g.start();
		}
		
	}
	
	private class GuiScriptRunnable implements Runnable {
		
		private Controller controller;
		
		GuiScriptRunnable(Controller controller) {
			this.controller = controller;
		}

		@Override
		public void run() {
			GuiScript g = new GuiScript(controller);
			g.setup();
			Frame f = controller.getGui().getFrame().getFrame();
			Rectangle r = f.getBounds();
			g.getFrame().getFrame().setLocation((int)r.getMaxX() + 10, (int)r.getMinY());
			controller.addTask(g);
			g.start();
		}
		
	}

}
