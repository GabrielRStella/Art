package com.ralitski.art.api;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ArtCanvas {
	
	private BufferedImage image;
	
	public ArtCanvas(Artist a) {
		this(a.getWidth(), a.getHeight());
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
	
	public void setAlpha(int x, int y, int alpha) {
		WritableRaster raster = image.getAlphaRaster();
		if(raster != null) {
			raster.setSample(x, y, 0, alpha);
		}
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
		setAlpha(x, y, color.getAlpha());
	}
}
