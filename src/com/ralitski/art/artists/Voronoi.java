package com.ralitski.art.artists;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.api.Artist;
import com.ralitski.art.api.Distance;
import com.ralitski.art.api.Point2d;
import com.ralitski.art.core.Settings;

//TODO: cleanup and use settings
public class Voronoi implements Artist {

	@Override
	public int getWidth(Settings settings) {
		return settings.getInt("width", 500);
	}

	@Override
	public int getHeight(Settings settings) {
		return settings.getInt("height", 500);
	}

	@Override
	public void draw(ArtCanvas canvas, Settings settings) {
		int NODES = settings.getInt("NODES_MIN", 7);
		int R_NODES = settings.getInt("NODES_RAND", 5);
		int COLOR = settings.getInt("COLOR", 550);
		int MIN_COLOR = settings.getInt("COLOR_MIN", 80);
		Distance d = settings.getEnum("METRIC", Distance.WONKY, Distance.class);
		settings.addComment("METRIC", "Metrics: " + Distance.types());
		
		Random random = new Random();
		List<Node> nodes = new LinkedList<>();
		float w = getWidth(settings);
		float h = getHeight(settings);
		for(int i = 0; i < NODES + random.nextInt(R_NODES); i++) {
			Node n = new Node(random.nextFloat() * w, random.nextFloat() * h);
			n.setColor(random, COLOR, MIN_COLOR);
			nodes.add(n);
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
				canvas.setColor(x, y, min.color);
			}
		}
	}
	
	private class Node extends Point2d {
		
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
			} while(r + g + b > COLOR || !(check(r, g, b) || check(g, b, r) || check(b, r, g)) || r + g + b < MIN_COLOR);
			color = new Color(r, g, b);
		}
		
		public boolean check(int a, int b, int c) {
			return a - b - c > 0;
		}
	}

}
