package com.ralitski.art.artists;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.api.Artist;
import com.ralitski.art.core.Settings;

public class Runes implements Artist {

	@Override
	public int getWidth(Settings settings) {
		return settings.getInt("img_width", 100);
	}

	@Override
	public int getHeight(Settings settings) {
		return settings.getInt("img_height", 100);
	}

	@Override
	public void draw(ArtCanvas canvas, Settings settings) {
	}

}
