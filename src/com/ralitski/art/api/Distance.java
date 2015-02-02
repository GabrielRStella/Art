package com.ralitski.art.api;

public enum Distance {
	EUCLIDEAN {
		@Override
		public float distance(Point2d p, Point2d p2) {
			return p2.distance(p);
		}
	},
	NOT_EUCLIDEAN {
		@Override
		public float distance(Point2d p, Point2d p2) {
			return -p2.distance(p);
		}
	},
	SQUARED {
		@Override
		public float distance(Point2d p, Point2d p2) {
			return p2.distanceSquared(p);
		}
	},
	ROOT {
		@Override
		public float distance(Point2d p, Point2d p2) {
			return (float)Math.sqrt(p2.distance(p));
		}
	},
	TAXICAB {
		@Override
		public float distance(Point2d p, Point2d p2) {
			float x = Math.abs(p2.getX() - p.getX());
			float y = Math.abs(p2.getY() - p.getY());
			return x + y;
		}
	},
	//the infinity norm (also known as Chebyshev distance)
	INFINITE {
		@Override
		public float distance(Point2d p, Point2d p2) {
			float x = Math.abs(p2.getX() - p.getX());
			float y = Math.abs(p2.getY() - p.getY());
			return Math.max(x, y);
		}
	},
	//the opposite of the infinity norm (Math.min)
	NEAREST {
		@Override
		public float distance(Point2d p, Point2d p2) {
			float x = Math.abs(p2.getX() - p.getX());
			float y = Math.abs(p2.getY() - p.getY());
			return Math.min(x, y);
		}
	},
	//relative distances from the origin
	ORIGINAL {
		@Override
		public float distance(Point2d p, Point2d p2) {
			return Math.abs(p.length() - p2.length());
		}
	},
	WONKY {
		@Override
		public float distance(Point2d p, Point2d p2) {
			return EUCLIDEAN.distance(p, p2) + INFINITE.distance(p, p2);
		}
	};
	
	public abstract float distance(Point2d p, Point2d p2);
}
