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
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}

	public void setSize(int width, int height) {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	public void setImage(BufferedImage image) {
		this.image = image;
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
	
	public void setColorBlend(int x, int y, Color color) {
		int i = image.getRGB(x, y);
		Color c = new Color(i, true);
		setColor(x, y, blend(color, c));
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

	public void copy(ArtCanvas canvas) {
		int maxX = Math.min(canvas.getWidth(), getWidth());
		int maxY = Math.min(canvas.getHeight(), getHeight());
		BufferedImage i2 = canvas.image;
		for(int x = 0; x < maxX; x++) {
			for(int y = 0; y < maxY; y++) {
				setColorBlend(x, y, new Color(i2.getRGB(x, y), true));
			}
		}
	}
	
	public static Color blend(Color src, Color dst) {
		float cr1 = (float)src.getRed() / 255F;
		float cg1 = (float)src.getGreen() / 255F;
		float cb1 = (float)src.getBlue() / 255F;
		float ca1 = (float)src.getAlpha() / 255F;
		float cr2 = (float)dst.getRed() / 255F;
		float cg2 = (float)dst.getGreen() / 255F;
		float cb2 = (float)dst.getBlue() / 255F;
		float ca2 = (float)dst.getAlpha() / 255F;
		float srcScale = ca1;
		float dstScale = Math.min(1F - srcScale, ca2);
		return new Color(cr1 * srcScale + cr2 * dstScale, cg1 * srcScale + cg2 * dstScale, cb1 * srcScale + cb2 * dstScale);
	}
}
