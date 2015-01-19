package com.ralitski.art.api;

import com.ralitski.art.core.Settings;

public interface PixelArtist {
	int getWidth();
	int getHeight();
	int getColor(int x, int y, Settings settings);
}
