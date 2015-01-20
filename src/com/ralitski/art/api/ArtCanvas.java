package com.ralitski.art.api;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.ralitski.art.core.Settings;

public class ArtCanvas {
	
	private BufferedImage image;
	
	public ArtCanvas(Artist a, Settings s) {
		this(a.getWidth(s), a.getHeight(s));
	}
	
	public ArtCanvas(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public Graphics2D getGraphics() {
		return image.createGraphics();
	}
	
	public void setColor(int x, int y, int color) {
		image.setRGB(x, y, color);
	}
	
	public void setColor(int x, int y, int red, int green, int blue) {
		int color = (red << 16) | (green << 8) | blue;
		setColor(x, y, color);
	}
	
	public void setColor(int x, int y, Color color) {
		setColor(x, y, color.getRGB());
	}
}
