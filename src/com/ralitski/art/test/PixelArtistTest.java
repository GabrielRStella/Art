package com.ralitski.art.test;
import com.ralitski.art.api.PixelArtist;

public class PixelArtistTest implements PixelArtist {
	
	public PixelArtistTest() {}

	@Override
	public int getWidth() {
		return 255;
	}

	@Override
	public int getHeight() {
		return getWidth();
	}

	@Override
	public int getColor(int x, int y) {
		int z = x * y;
		return x + (y << 8) + (z << 16);
	}

}
