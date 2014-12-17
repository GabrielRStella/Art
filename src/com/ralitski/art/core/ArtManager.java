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
	
	private Class<?> artClass;
	private Artist artist;
	BufferedImage image;
	
	//running
	private boolean setup;
	private volatile boolean running;
	private FrameManager frame;

    //TODO: produce the art and stuff
	
	public ArtManager(Class<?> c) throws Exception {
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
		ArtCanvas canvas = new ArtCanvas(artist);
		artist.draw(canvas);
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
		frame.setup();
		running = true;
		return setup = true;
	}
	
	public boolean running() {
		return running;
	}

    public void start() {
        if(!setup) return;
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
    
    public Image getImage() {
    	return image;
    }
    
    public ArtManager clone() {
    	try {
			return new ArtManager(artClass);
		} catch (Exception e) {
			//wont happen
			return null;
		}
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
}
