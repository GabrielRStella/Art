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
	public ArtCanvas draw(Settings settings) {
		int w = internal.getWidth(settings);
		int h = internal.getHeight(settings);
		ArtCanvas canvas = new ArtCanvas(w, h);
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				int c = internal.getColor(x, y, settings);
				canvas.setColor(x, y, c);
			}
		}
		return canvas;
	}

}
