package com.ralitski.art.artists;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.ralitski.art.api.ArtCanvas;
import com.ralitski.art.api.Artist;
import com.ralitski.art.core.Settings;

public class Expander implements Artist {

	@Override
	public ArtCanvas draw(Settings settings) {
		String pathIn = settings.get("image_in", "./image.png");
		String pathOut = settings.get("image_out", "./image2.png");
		int scale = settings.getInt("scale", 16);
		File f = new File(pathIn);
		File f2 = new File(pathOut);
		if(f.exists()) {
			try {
				BufferedImage image = ImageIO.read(f);
				f2.createNewFile();
				BufferedImage image2 = new BufferedImage(image.getWidth() * scale, image.getHeight() * scale, image.getType());
				ArtCanvas canvas = new ArtCanvas(image2);
				//le loops
				for(int i = 0; i < image.getWidth(); i++) {
					for(int j = 0; j < image.getHeight(); j++) {
						for(int px = 0; px < scale; px++) {
							for(int py = 0; py < scale; py++) {
								int x = i * scale + px;
								int y = j * scale + py;
								image2.setRGB(x, y, image.getRGB(i, j));
							}
						}
					}
				}
				ImageIO.write(image2, settings.get("format", "png"), f2);
				return canvas;
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("No file found to be expanded");
		}
		return null;
	}

}
