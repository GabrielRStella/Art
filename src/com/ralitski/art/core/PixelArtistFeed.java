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
	public int getWidth(Settings settings) {
		return internal.getWidth(settings);
	}

	@Override
	public int getHeight(Settings settings) {
		return internal.getHeight(settings);
	}

	@Override
	public void draw(ArtCanvas canvas, Settings settings) {
		for(int x = 0; x < getWidth(settings); x++) {
			for(int y = 0; y < getHeight(settings); y++) {
				int c = internal.getColor(x, y, settings);
				canvas.setColor(x, y, c);
			}
		}
	}

}
