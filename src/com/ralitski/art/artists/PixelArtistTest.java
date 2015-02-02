package com.ralitski.art.artists;
import com.ralitski.art.api.PixelArtist;
import com.ralitski.art.core.Settings;

public class PixelArtistTest implements PixelArtist {
	
	public PixelArtistTest() {}

	@Override
	public int getWidth(Settings settings) {
		return settings.getInt("width", 255);
	}

	@Override
	public int getHeight(Settings settings) {
		return settings.getInt("height", 255);
	}

	@Override
	public int getColor(int x, int y, Settings settings) {
		int z = x * y;
		return (x + (y << 8) + (z << 16)) | 0xFF000000; //correct alpha
	}

}
