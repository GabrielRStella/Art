package com.ralitski.art.test;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.api.Artist;
import com.ralitski.art.api.Distance;
import com.ralitski.art.api.Point2d;

public class Voronoi implements Artist {

	private static final int NODES = 7;
	private static final int R_NODES = 5;
	private static final int COLOR = 550;
	private static final int MIN_COLOR = 80;

	@Override
	public int getWidth() {
		return 500;
	}

	@Override
	public int getHeight() {
		return getWidth();
	}

	@Override
	public void draw(ArtCanvas canvas) {
		Random random = new Random();
		List<Node> nodes = new LinkedList<>();
		float w = getWidth();
		float h = getHeight();
		for(int i = 0; i < NODES + random.nextInt(R_NODES); i++) {
			Node n = new Node(random.nextFloat() * w, random.nextFloat() * h);
			n.setColor(random);
			nodes.add(n);
		}
		Distance d = Distance.WONKY;
		for(int x = 0; x < getWidth(); x++) {
			for(int y = 0; y < getHeight(); y++) {
				Point2d p = new Point2d(x, y);
				Node min = null;
				float minDist = Float.MAX_VALUE;
				for(Node n : nodes) {
					float dist = d.distance(p, n);
					if(min == null || dist < minDist) {
						min = n;
						minDist = dist;
					}
				}
				canvas.setColor(x, y, min.color);
			}
		}
	}
	
	private class Node extends Point2d {
		
		private Color color;
		
		public Node(float x, float y) {
			super(x, y);
		}
		
		public void setColor(Random rand) {
			int r;
			int g;
			int b;
			do {
				r = rand.nextInt(255);
				g = rand.nextInt(255);
				b = rand.nextInt(255);
			} while((r + g + b > COLOR && !(check(r, g, b) || check(g, b, r) || check(b, r, g))) || r + g + b < MIN_COLOR);
			color = new Color(r, g, b);
		}
		
		public boolean check(int a, int b, int c) {
			return a - b - c > 0;
		}
	}

}
