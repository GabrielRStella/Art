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
public class VoronoiSmooth implements Artist {

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
		int NODES = settings.getInt("NODES_MIN", 10);
		int R_NODES = settings.getInt("NODES_RAND", 16);
		Distance d = settings.getEnum("METRIC", Distance.EUCLIDEAN, Distance.class);
		float scale = settings.getInt("scale", 4);
		scale = 1F/scale;
		
		Random random = new Random();
		List<Node> nodes = new LinkedList<>();
		float w = getWidth(settings);
		float h = getHeight(settings);
		for(int i = 0; i < NODES + random.nextInt(R_NODES); i++) {
			Node n = new Node(random.nextFloat() * w, random.nextFloat() * h, random);
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
				min.maxDist = Math.max(min.maxDist, minDist);
			}
		}
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				Point2d p = new Point2d(x, y);
				float r = 0;
				float g = 0;
				float b = 0;
				for(Node n : nodes) {
					float dist = getDist(p, n, d, w, h);
					dist /= n.maxDist * scale;
					float r2 = n.color.getRed();
					r2 = r2 / 255F / dist;
					float g2 = n.color.getGreen();
					g2 = g2 / 255F / dist;
					float b2 = n.color.getBlue();
					b2 = b2 / 255F / dist;

					r += r2;
					g += g2;
					b += b2;
				}
				r = Math.min(r, 1);
				g = Math.min(g, 1);
				b = Math.min(b, 1);
				canvas.setColor(x, y, new Color(r, g, b));
			}
		}
	}
	
	private float getDist(Point2d p, Point2d p2, Distance d, float w, float h) {
//		float min = Float.MAX_VALUE;
//		for(int i = -1; i <= 1; i++) {
//			for(int j = -1; j <= 1; j++) {
//				float x = i;
//				float y = j;
//				x *= w;
//				y *= h;
//				Point2d p3 = new Point2d(p2.getX() + x, p2.getY() + y);
//				float dist = d.distance(p, p3);
//				if(dist < min) min = dist;
//			}
//		}
		return d.distance(p, p2);
	}
	
	private static class Node extends Point2d {
		
		private static Color[] colors = new Color[]{
			Color.RED,
			Color.GREEN,
			Color.BLUE
		};
		
		private float maxDist;
		private Color color;
		
		public Node(float x, float y, Random rand) {
			super(x, y);
			int i = rand.nextInt(colors.length);
			color = colors[i];
		}
	}

}
