package com.ralitski.art.test;

import java.awt.Color;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.api.Artist;
import com.ralitski.art.api.Distance;
import com.ralitski.art.api.Point2d;
import com.ralitski.art.core.Settings;

public class Voronoi2 implements Artist {

	private static final int NODES = 10;
	private static final int R_NODES = 16;
	private static final int COLOR = 700;
	private static final int MIN_COLOR = 150;

	@Override
	public int getWidth() {
		return 1000;
	}

	@Override
	public int getHeight() {
		return getWidth();
	}

	@Override
	public void draw(ArtCanvas canvas, Settings settings) {
		Random random = new Random();
		List<Node> nodes = new LinkedList<>();
		Map<Node, List<Point2d>> sections = new HashMap<>();
		float w = getWidth();
		float h = getHeight();
		for(int i = 0; i < NODES + random.nextInt(R_NODES); i++) {
			Node n = new Node(random.nextFloat() * w, random.nextFloat() * h);
			n.setColor(random);
			nodes.add(n);
			sections.put(n, new LinkedList<Point2d>());
		}
		Distance d = Distance.TAXICAB;
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
				sections.get(min).add(p);
				min.maxDist = Math.max(min.maxDist, minDist);
			}
		}
		for(Entry<Node, List<Point2d>> section : sections.entrySet()) {
			Node n = section.getKey();
			for(Point2d p : section.getValue()) {
				float grade = d.distance(p, n) / n.maxDist;
				Color c = n.color;
				float red = (float)c.getRed() / 255F * grade;
				float green = (float)c.getGreen() / 255F * grade;
				float blue = (float)c.getBlue() / 255F * grade;
				c = new Color(red, green, blue);
				int x = (int)p.getX();
				int y = (int)p.getY();
				canvas.setColorNoAlpha(x, y, c);
			}
		}
	}
	
	private class Node extends Point2d {
		
		private float maxDist;
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
			} while((r + g + b > COLOR && !(check(r, g, b) || check(g, b, r) || check(b, r, g))));
			color = new Color(r, g, b);
		}
		
		public boolean check(int a, int b, int c) {
			return a - b - c > MIN_COLOR;
		}
	}

}
