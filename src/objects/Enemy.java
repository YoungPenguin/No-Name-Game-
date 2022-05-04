/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import No.Name.Game.v3.Game;
import texture.Texture;

/**
 *
 * @author Janus
 */

public class Enemy extends Objekt {
	Texture tex = Game.getInstance();

	private int type;

	public Enemy(float x, float y, ObjektId id) {
		super(x, y, id);
	}

	@Override
	public void tick(LinkedList<Objekt> objekt) {

	}

	@Override
	public void render(Graphics g) {
		if (type == 0)
			g.drawImage(tex.spriteSheetImage[3], (int) x, (int) y, null);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

}
