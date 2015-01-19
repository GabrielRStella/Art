package com.ralitski.art.api;

import com.ralitski.art.core.Settings;

public interface Artist {
	int getWidth();
	int getHeight();
	void draw(ArtCanvas canvas, Settings settings);
}
