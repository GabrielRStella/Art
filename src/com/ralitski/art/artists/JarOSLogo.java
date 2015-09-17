package com.ralitski.art.artists;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.api.Artist;
import com.ralitski.art.core.Settings;

public class JarOSLogo implements Artist {

	@Override
	public ArtCanvas draw(Settings settings) {
		int w = settings.getInt("img_width", 100);
		int h = settings.getInt("img_height", 100);
		ArtCanvas canvas = new ArtCanvas(w, h);
		return canvas;
	}

}
