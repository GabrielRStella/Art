package com.ralitski.art.artists.dungeon;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.core.Settings;

public interface DungeonComponent {

	void render(ArtCanvas canvas, Settings settings, int tileX, int tileY, int meta);
}
