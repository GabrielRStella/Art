package com.ralitski.art.api;

import com.ralitski.art.core.Settings;

public interface PixelArtist {
	int getWidth(Settings settings);
	int getHeight(Settings settings);
	int getColor(int x, int y, Settings settings);
}
