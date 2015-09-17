package com.ralitski.art.artists.dungeon;

import java.awt.Color;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.api.Artist;
import com.ralitski.art.core.Settings;

/**
 * TODO: materials that paint the dungeon based on pixel position
 * 
 * @author ralitski
 *
 */
public class DungeonMaker implements Artist {
	
	public static final int TILE_SIZE = 17;
	public static final Color GRID_COLOR = Color.GRAY;
	
	public static int getImgSize(int tiles) {
		return 1 + (TILE_SIZE - 1) * tiles;
	}
	
	public static int getTileLocation(int tile) {
		return tile * (TILE_SIZE - 1);
	}
	
	private ArtCanvas canvas;
	private Settings settings;
	
	private int tiles_x;
	private int tiles_y;
	private int width;
	private int height;
	private DungeonComponent[][] components;
	private int[][] componentMeta;

	@Override
	public ArtCanvas draw(Settings settings) {
		int w = getImgSize(settings.getInt("NUM_TILES_X", 20));
		int h = getImgSize(settings.getInt("NUM_TILES_Y", 20));
		canvas = new ArtCanvas(w, h);
		width = canvas.getWidth();
		height = canvas.getHeight();
		this.settings = settings;
		tiles_x = settings.getInt("NUM_TILES_X", 20);
		tiles_y = settings.getInt("NUM_TILES_Y", 20);
		drawTileGrid();
		//generate map
		components = new DungeonComponent[tiles_x][tiles_y];
		componentMeta = new int[tiles_x][tiles_y];
		new DungeonGenerator().generate(settings, components, componentMeta);
		//draw map
		for(int i = 0; i < tiles_x; i++) {
			for(int j = 0; j < tiles_y; j++) {
				DungeonComponent component = components[i][j];
				if(component != null) {
					component.render(canvas, settings, i, j, componentMeta[i][j]);
				}
			}
		}
		return canvas;
	}	
	
	private void drawTileGrid() {
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(checkGrid(i) || checkGrid(j)) {
					canvas.setColor(i, j, GRID_COLOR);
				}
			}
		}
	}
	
	private boolean checkGrid(int x) {
		return (x % (TILE_SIZE - 1)) == 0;
	}

}
