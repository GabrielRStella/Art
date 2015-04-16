package com.ralitski.art.api;

import com.ralitski.art.core.Settings;

public interface Artist {
	int getWidth(Settings settings);
	int getHeight(Settings settings);
	void draw(ArtCanvas canvas, Settings settings);
}
