package com.ralitski.art.core;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.api.Artist;
import com.ralitski.art.api.PixelArtist;


public class ArtManager implements Cloneable {
	
	private Controller controller;
	
	private Class<?> artClass;
	private Artist artist;
	BufferedImage image;
	
	//running
	private boolean setup;
	private volatile boolean running;
	private FrameManager frame;

    //TODO: produce the art and stuff
	
	public ArtManager(Controller controller, Class<?> c) throws Exception {
		this.controller = controller;
		this.artClass = c;
		try {
			Object o = c.newInstance();
			artist = getArtist(o);
		} catch (InstantiationException e) {
			System.err.println("Unable to instantiate class " + c.getName());
		}
	}
	
	//running
	
	public boolean setup() {
		if(artist == null) throw new IllegalStateException("Art requires an Artist");
		Settings s = getSettings();
		ArtCanvas canvas = new ArtCanvas(artist, s);
		artist.draw(canvas, s);
		image = canvas.getImage();
		
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
		frame.setup(s);
		running = true;
		return setup = true;
	}
	
	public boolean running() {
		return running;
	}

    public void start() {
        if(!setup) return;
        artClass = null; //allow new art to be generated
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
    	return artClass.getSimpleName();
    }
    
    public Artist getArtist() {
    	return artist;
    }
    
    public Settings getSettings() {
    	return controller.getSettings().getSubSettings(getName());
    }
    
    public Image getImage() {
    	return image;
    }
    
    //statik
    
    private static Artist getArtist(Object o) {
    	if(o instanceof Artist) {
    		return (Artist)o;
    	} else if(o instanceof PixelArtist) {
    		return new PixelArtistFeed((PixelArtist)o);
    	} else {
    		return null;
    	}
    }
	
	public static void createArt(Controller controller, Class<?> c) {
		try {
			final ArtManager manager = new ArtManager(controller, c);
			if(manager.getArtist() != null) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isArtist(Class<?> c) {
		return Artist.class.isAssignableFrom(c) || PixelArtist.class.isAssignableFrom(c);
	}
}
