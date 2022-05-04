/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package texture;

import java.awt.image.BufferedImage;

/**
 *
 * @author Janus
 */
public class Texture {

	SpriteSheet sp;
	private BufferedImage spriteSheet = null;

	public BufferedImage[] spriteSheetImage = new BufferedImage[8];

	public Texture() {

		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("/sprite_sheet.png");
		} catch (Exception e) {
			e.printStackTrace();
		}

		sp = new SpriteSheet(spriteSheet);
		getTextures();
	}

	private void getTextures() {
		spriteSheetImage[0] = sp.grabImage(1, 1, 32, 32); // blok på de
															// kortinator
		spriteSheetImage[1] = sp.grabImage(2, 1, 32, 32); // blok på de
															// kortinator
		spriteSheetImage[2] = sp.grabImage(3, 1, 32, 32); // blok på de
															// kortinator
		spriteSheetImage[3] = sp.grabImage(4, 1, 32, 32); // blok på de
															// kortinator
		spriteSheetImage[4] = sp.grabImage(5, 1, 32, 32); // blok på de
		spriteSheetImage[5] = sp.grabImage(6, 1, 32, 32); // blok på de
		spriteSheetImage[6] = sp.grabImage(7, 1, 32, 32); // blok på de
		spriteSheetImage[7] = sp.grabImage(8, 1, 32, 32); // blok på de
		// kortinator
	}
}
