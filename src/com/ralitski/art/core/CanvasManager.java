package com.ralitski.art.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class CanvasManager extends Canvas {

	/**
	 * autogenerated by eclipse
	 */
	private static final long serialVersionUID = -1375071898781748364L;
	
	private Image image;
	
	public CanvasManager(Image i) {
		image = i;
	}

    @Override
    public final void removeNotify() {
        System.out.println("Window destroyed");
        super.removeNotify();
    }

    @Override
    public void paint(Graphics g) {
    	g.setColor(Color.white);
    	g.drawImage(image, 0, 0, this);
    }

}
