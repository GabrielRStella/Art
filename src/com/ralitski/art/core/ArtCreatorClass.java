package com.ralitski.art.core;

import java.awt.image.BufferedImage;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.api.Artist;
import com.ralitski.art.api.PixelArtist;

public class ArtCreatorClass implements ArtCreator {
	
	private Class<?> artClass;
	private Artist artist;
	
	public ArtCreatorClass(Class<?> c) {
		this.artClass = c;
		try {
			Object o = c.newInstance();
			artist = getArtist(o);
		} catch (Exception e) {
			System.err.println("Unable to instantiate class " + c.getName());
		}
	}
    
	@Override
    public String getName() {
    	return artClass.getSimpleName();
    }
    
    public Artist getArtist() {
    	return artist;
    }

	@Override
	public BufferedImage drawImage(Settings s) {
		if(artist == null) throw new IllegalStateException("Art requires an Artist");
		ArtCanvas canvas = new ArtCanvas(artist, s);
		artist.draw(canvas, s);
		return canvas.getImage();
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
	
	public static boolean isArtist(Class<?> c) {
		return Artist.class.isAssignableFrom(c) || PixelArtist.class.isAssignableFrom(c);
	}

}
