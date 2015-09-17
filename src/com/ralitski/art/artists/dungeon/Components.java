package com.ralitski.art.artists.dungeon;

import java.awt.Color;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.core.Settings;

public class Components {
	
	private static final Color color = Color.BLACK;

	public static final DungeonComponent WALL = new ComponentWall();
	public static final DungeonComponent DOOR = new ComponentDoor();
	public static final DungeonComponent TRAP = new ComponentTrap();
	public static final DungeonComponent ENEMY = new ComponentEnemy();
	
	static {
		
	}
	
	private static class ComponentWall implements DungeonComponent {

		@Override
		public void render(ArtCanvas canvas, Settings settings, int tileX, int tileY, int meta) {
			tileX = DungeonMaker.getTileLocation(tileX);
			tileY = DungeonMaker.getTileLocation(tileY);
			for(int i = 0; i < DungeonMaker.TILE_SIZE; i++) {
				for(int j = 0; j < DungeonMaker.TILE_SIZE; j++) {
					canvas.setColor(tileX + i, tileY + j, color);
				}
			}
		}
	}
	
	private static class ComponentDoor implements DungeonComponent {

		@Override
		public void render(ArtCanvas canvas, Settings settings, int tileX, int tileY, int meta) {
			tileX = DungeonMaker.getTileLocation(tileX);
			tileY = DungeonMaker.getTileLocation(tileY);
			int tile = DungeonMaker.TILE_SIZE;
			int door = tile / 5;
			int center = (tile - 1) / 2;
			int start = center - ((door -1) / 2);
			if(meta % 2 == 0) {
				//horizontal
				for(int i = 0; i < tile; i++) {
					for(int j = start; j < start + door; j++) {
						canvas.setColor(tileX + i, tileY + j, color);
					}
				}
			} else {
				//vertical
				for(int j = 0; j < tile; j++) {
					for(int i = start; i < start + door; i++) {
						canvas.setColor(tileX + i, tileY + j, color);
					}
				}
			}
		}
		
	}
	
	private static class ComponentTrap implements DungeonComponent {

		@Override
		public void render(ArtCanvas canvas, Settings settings, int tileX, int tileY, int meta) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private static class ComponentEnemy implements DungeonComponent {

		@Override
		public void render(ArtCanvas canvas, Settings settings, int tileX, int tileY, int meta) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
