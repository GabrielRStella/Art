package com.ralitski.art.core;

import java.awt.image.BufferedImage;

public interface ArtCreator {
	String getName();
	BufferedImage drawImage(Settings s);
}
