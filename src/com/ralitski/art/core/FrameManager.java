package com.ralitski.art.core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import com.ralitski.art.api.Artist;

public class FrameManager implements WindowListener {

    //TODO: produce the art and stuff
	
	private ArtManager manager;
	
	private Frame frame;
	private Canvas canvas;
	private volatile boolean running;
	
	public FrameManager(ArtManager manager) {
		this.manager = manager;
	}
	
	public void setup() {
		//for width and height
        Artist a = manager.getArtist();

        this.frame = new Frame();
        frame.setTitle(manager.getName());
        frame.setBackground(java.awt.Color.BLACK);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        
        //make sure the inside of the frame is the proper size
        Insets insets = frame.getInsets();
        //adjustment constant, idk why it's necessary (but it is)
        int cons = 2;
        int width = a.getWidth() + insets.left - cons;
        int height = a.getHeight() + insets.top - cons;
        frame.setSize(width, height);
        frame.setResizable(false);
        
		canvas = new CanvasManager(manager.getImage());
        canvas.setFocusable(true);
        canvas.requestFocus();

        frame.add(canvas);
        frame.addWindowListener(this);

        running = true;
        System.out.println("Window created!");
	}
	
	public void stop() {
		if(running) {
			frame.dispose();
			running = false;
			if(manager.running()) {
				manager.stop();
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
