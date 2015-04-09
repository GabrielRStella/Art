package com.ralitski.art.core;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class ArtManager implements Cloneable {
	
	private Controller controller;
	private ArtCreator creator;
	
	BufferedImage image;
	
	//running
	private boolean setup;
	private volatile boolean running;
	private FrameManager frame;
	
	public ArtManager(Controller controller, ArtCreator creator) {
		this.controller = controller;
		this.creator = creator;
	}
	
	//running
	
	public boolean setup() {
		Settings s = getSettings();
		image = creator.drawImage(s);
		
		try {
			File f = new File("./art/" + getName() + ".png");
			f.getParentFile().mkdirs();
			if(f.exists()) f.delete();
			f.createNewFile();
			ImageIO.write(image, "png", f);
		} catch (IOException e) {
			System.err.println("Unable to save image \"" + getName() + "\"");
		}
		
		frame = new FrameManager(this);
		frame.setup(image.getWidth(), image.getHeight());
		running = true;
		return setup = true;
	}
	
	public boolean running() {
		return running;
	}

    public void start() {
        if(!setup) return;
        creator = null; //allow new art to be generated
        while (running && frame.running()) {
        	//uh...
        }
        this.frame.stop();
        running = false;
        setup = false;
    }

    public void stop() {
        running = false;
    }
    
    //misc
    
    public String getName() {
    	return creator.getName();
    }
    
    public Settings getSettings() {
    	return controller.getSettings().getSubSettings(getName());
    }
    
    public Image getImage() {
    	return image;
    }
    
    //statik
	
	public static void createArt(Controller controller, Class<?> c) {
		ArtCreatorClass creator = new ArtCreatorClass(c);
		if(creator.getArtist() != null) {
			ArtManager manager = new ArtManager(controller, creator);
			createArt(manager);
		}
	}
	
	public static void createArt(final ArtManager manager) {
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
}
