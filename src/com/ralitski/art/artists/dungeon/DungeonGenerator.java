package com.ralitski.art.artists.dungeon;

import com.ralitski.art.core.Settings;

public class DungeonGenerator {

	public void generate(Settings settings, DungeonComponent[][] components, int[][] componentMeta) {
		// TODO Auto-generated method stub
		components[0][0] = Components.WALL;
		components[0][1] = Components.DOOR;
		components[0][2] = Components.DOOR;
		componentMeta[0][2] = 1;
		components[0][3] = Components.TRAP;
		components[0][4] = Components.ENEMY;
		components[0][5] = Components.ENEMY;
		componentMeta[0][5] = 1;
	}

}
