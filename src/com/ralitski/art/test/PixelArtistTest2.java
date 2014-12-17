package com.ralitski.art.test;
import com.ralitski.art.api.PixelArtist;

public class PixelArtistTest2 implements PixelArtist {
	
	public PixelArtistTest2() {}

	@Override
	public int getWidth() {
		return 255;
	}

	@Override
	public int getHeight() {
		return getWidth();
	}

	@Override
	public int getColor(int i, int j) {
		int off = 255;
		i -= off;
		j -= off;
		float f = 2;
		float x = i;
		float y = j;
		x /= f;
		y /= f;
		float z = x * y * f;
		return (int)x + ((int)y << 8) + ((int)z << 16);
	}

}
