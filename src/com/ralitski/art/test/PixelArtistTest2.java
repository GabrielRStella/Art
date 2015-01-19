package com.ralitski.art.test;
import com.ralitski.art.api.PixelArtist;
import com.ralitski.art.core.Settings;

public class PixelArtistTest2 implements PixelArtist {
	
	public PixelArtistTest2() {}

	@Override
	public int getWidth(Settings settings) {
		return settings.getInt("width", 255);
	}

	@Override
	public int getHeight(Settings settings) {
		return settings.getInt("height", 255);
	}

	@Override
	public int getColor(int i, int j, Settings settings) {
		int off = 255;
		i -= off;
		j -= off;
		float f = 2;
		float x = i;
		float y = j;
		x /= f;
		y /= f;
		float z = x * y * f;
		return ((int)x + ((int)y << 8) + ((int)z << 16)) | 0xFF000000; //correct alpha
	}

}
