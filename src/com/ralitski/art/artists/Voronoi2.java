package com.ralitski.art.artists;

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

//TODO: cleanup and use settings
public class Voronoi2 implements Artist {

	@Override
	public ArtCanvas draw(Settings settings) {
		int w =  settings.getInt("width", 500);
		int h = settings.getInt("height", 500);
		ArtCanvas canvas = new ArtCanvas(w, h);
		int NODES = settings.getInt("NODES_MIN", 10);
		int R_NODES = settings.getInt("NODES_RAND", 16);
		int COLOR = settings.getInt("COLOR", 700);
		int MIN_COLOR = settings.getInt("COLOR_MIN", 150);
		Distance d = settings.getEnum("METRIC", Distance.TAXICAB, Distance.class);
		
		Random random = new Random();
		List<Node> nodes = new LinkedList<>();
		Map<Node, List<Point2d>> sections = new HashMap<>();
		for(int i = 0; i < NODES + random.nextInt(R_NODES); i++) {
			Node n = new Node(random.nextFloat() * w, random.nextFloat() * h);
			n.setColor(random, COLOR, MIN_COLOR);
			nodes.add(n);
			sections.put(n, new LinkedList<Point2d>());
		}
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
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
				canvas.setColor(x, y, c);
			}
		}
		return canvas;
	}
	
	private class Node extends Point2d {
		
		private float maxDist;
		private Color color;
		
		public Node(float x, float y) {
			super(x, y);
		}
		
		public void setColor(Random rand, int COLOR, int MIN_COLOR) {
			int r;
			int g;
			int b;
			do {
				r = rand.nextInt(255);
				g = rand.nextInt(255);
				b = rand.nextInt(255);
			} while(r + g + b > COLOR && !(check(r, g, b, MIN_COLOR) || check(g, b, r, MIN_COLOR) || check(b, r, g, MIN_COLOR)) );
			color = new Color(r, g, b);
		}
		
		public boolean check(int a, int b, int c, int MIN_COLOR) {
			return a - b - c > MIN_COLOR;
		}
	}

}
