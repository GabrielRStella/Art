package com.ralitski.art.core.gui;

import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;

import com.ralitski.art.core.Task;

public class GuiFrame implements WindowListener {

    //TODO: produce the art and stuff
	
	private Task task;
	
	private Frame frame;
	private volatile boolean running;
	
	public GuiFrame(Task task) {
		this.task = task;
	}
	
	Frame getFrame() {
		return frame;
	}
	
	public void setup() {
		//for width and height

        this.frame = new Frame();
        frame.setTitle("Art");
        frame.setBackground(java.awt.Color.WHITE);
        frame.setLayout(new BoxLayout(frame, BoxLayout.X_AXIS));
        frame.setVisible(true);
        
//        //make sure the inside of the frame is the proper size
//        Insets insets = frame.getInsets();
//        //adjustment constant, idk why it's necessary (but it is)
//        int cons = 2;
//        int width = gui.getWidth() + insets.left - cons;
//        int height = gui.getHeight() + insets.top - cons;
//        frame.setSize(width, height);
        
        frame.setResizable(false);
        frame.addWindowListener(this);

        running = true;
	}
	
	public void stop() {
		if(running) {
			frame.dispose();
			running = false;
			if(task.running()) {
				task.stop();
			}
		}
	}
	
	public boolean running() {
		return running;
	}

    @Override
    public void windowClosing(WindowEvent event) {
        //stop requested (close button)
        this.stop();
    }

    @Override
    public void windowActivated(WindowEvent event) {
        // TODO window focused
    }

    @Override
    public void windowDeactivated(WindowEvent event) {
        // TODO window focus lost
    }

    @Override
    public void windowClosed(WindowEvent event) {
        // TODO window closed via call to dispose()
    }

    @Override
    public void windowDeiconified(WindowEvent event) {
        // TODO window selected from taskbar
    }

    @Override
    public void windowIconified(WindowEvent event) {
        // TODO window minimized to taskbar
    }

    @Override
    public void windowOpened(WindowEvent event) {
        // TODO window created
    }
}
