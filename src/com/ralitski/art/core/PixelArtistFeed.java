package com.ralitski.art.core;

import java.awt.image.WritableRaster;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.api.Artist;
import com.ralitski.art.api.PixelArtist;

public class PixelArtistFeed implements Artist {
	
	private PixelArtist internal;
	
	public PixelArtistFeed(PixelArtist artist) {
		internal = artist;
	}

	@Override
	public int getWidth() {
		return internal.getWidth();
	}

	@Override
	public int getHeight() {
		return internal.getHeight();
	}

	@Override
	public void draw(ArtCanvas canvas) {
		WritableRaster alpha = canvas.getImage().getAlphaRaster();
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				int c = internal.getColor(x, y);
				canvas.setColor(x, y, c);
				if(alpha != null) {
					alpha.setSample(x, y, 0, 255);
				}
			}
		}
	}

}
