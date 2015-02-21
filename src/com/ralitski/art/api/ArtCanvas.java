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
		//is actually argb
		image.setRGB(x, y, color);
	}
	
	public void setColor(int x, int y, int red, int green, int blue) {
		int color = (red << 16) | (green << 8) | blue | 0xFF000000;
		setColor(x, y, color);
	}
	
	public void setColor(int x, int y, int red, int green, int blue, int alpha) {
		int color = (alpha << 24) | (red << 16) | (green << 8) | blue;
		setColor(x, y, color);
	}
	
	public void setColor(int x, int y, Color color) {
		setColor(x, y, color.getRGB());
	}
	
	public void setColor(int x, int y, float r, float g, float b) {
		int rI = (int)Math.floor(r * 255F);
		int gI = (int)Math.floor(g * 255F);
		int bI = (int)Math.floor(b * 255F);
		setColor(x, y, rI, gI, bI);
	}
	
	public void setColor(int x, int y, float r, float g, float b, float a) {
		int rI = (int)Math.floor(r * 255F);
		int gI = (int)Math.floor(g * 255F);
		int bI = (int)Math.floor(b * 255F);
		int aI = (int)Math.floor(a * 255F);
		setColor(x, y, rI, gI, bI, aI);
	}
}
