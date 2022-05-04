/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package No.Name.Game.v3;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import objects.Objekt;
import objects.ObjektId;
import objects.Player;

/**
 *
 * @author Janus
 */
public class KeyInput extends KeyAdapter {

	private Handler handler;

	public KeyInput(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_ENTER && !Game.start) {
			Game.loadLevel();
			Game.start = true;
		}

		if (key == KeyEvent.VK_ENTER && Player.lives == 0) {
			handler.clearLevel();
			Game.loadLevel();
			Player.lives = 3;
			Game.start = true;
		}
		if (Player.lives > 0) {
			for (int i = 0; i < handler.objekt.size(); i++) {
				Objekt tempObjekt = handler.objekt.get(i);

				if (tempObjekt.getId() == ObjektId.PLAYER) {
					if (key == KeyEvent.VK_RIGHT) {
						tempObjekt.setVelX(5);
						tempObjekt.setTexture(Game.getInstance().spriteSheetImage[4]);
					}
					if (key == KeyEvent.VK_LEFT) {
						tempObjekt.setVelX(-5);
						tempObjekt.setTexture(Game.getInstance().spriteSheetImage[5]);
					}
					if (key == KeyEvent.VK_SPACE && !tempObjekt.isJumping()) {
						tempObjekt.setJumping(true);
						if (key == KeyEvent.VK_SPACE)
							tempObjekt.setVelY(-10);
					}
				}
			}
		}

		if (key == KeyEvent.VK_ESCAPE) {
			System.exit(1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.objekt.size(); i++) {
			Objekt tempObjekt = handler.objekt.get(i);

			if (tempObjekt.getId() == ObjektId.PLAYER) {
				if (key == KeyEvent.VK_RIGHT)
					tempObjekt.setVelX(0);
				if (key == KeyEvent.VK_LEFT)
					tempObjekt.setVelX(0);
			}
		}
	}
}
