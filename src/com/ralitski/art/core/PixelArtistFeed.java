package com.ralitski.art.core;

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
	public void draw(ArtCanvas canvas, Settings settings) {
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				int c = internal.getColor(x, y, settings);
				canvas.setColor(x, y, c);
			}
		}
	}

}
