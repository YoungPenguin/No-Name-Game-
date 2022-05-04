/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import No.Name.Game.v3.Game;
import No.Name.Game.v3.Handler;
import texture.Texture;

/**
 *
 * @author Janus
 */
public class Player extends Objekt {

	private float width = 32, height = 32;

	public static int lives = 3; ///////////////////////////////// liv
	private float gravity = 0.5f;
	private final float MAX_SPEED = 10;
	private Handler handler;
	boolean hit = false;
	Texture tex = Game.getInstance();

	public Player(float x, float y, Handler handler, ObjektId id) {
		super(x, y, id);
		this.handler = handler;
		texture = tex.spriteSheetImage[4];

	}

	@Override
	public void tick(LinkedList<Objekt> objekt) {
		if (lives > 0) {
			x += velX;
			y += velY;

			if (falling || jumping) {
				velY += gravity;

				if (velY > MAX_SPEED)
					velY = MAX_SPEED;
			}
			Collision(objekt);
		} else {
			setTexture(tex.spriteSheetImage[6]);
		}
	}

	private void Collision(LinkedList<Objekt> objekt) {
		for (int i = 0; i < handler.objekt.size(); i++) {
			Objekt tempObjekt = handler.objekt.get(i);

			if (tempObjekt.getId() == ObjektId.BLOCK) {
				if (getBoundsTop().intersects(tempObjekt.getBounds())) {
					y = tempObjekt.getY() + (32);
					velY = 0;
				}

				if (getBounds().intersects(tempObjekt.getBounds())) {
					y = tempObjekt.getY() - height;
					velY = 0;
					falling = false;
					jumping = false;
				} else {
					falling = true;
				}
				/////////////////// højre

				if (getBoundsRight().intersects(tempObjekt.getBounds())) {
					x = tempObjekt.getX() - (width);
				}
				/////////////////// venstre

				if (getBoundsLeft().intersects(tempObjekt.getBounds())) {
					x = tempObjekt.getX() + (width);
				}

			} else if (tempObjekt.getId() == ObjektId.GOAL) {
				// skal stoppe spillet ------------------------------
				if (getBounds().intersects(tempObjekt.getBounds())) {
					handler.clearLevel();
					Game.levelnum++;
					Game.loadLevel();
				}
			} else if (tempObjekt.getId() == ObjektId.ENEMY) {

				// skal stoppe spillet ------------------------------
				if (getBounds().intersects(tempObjekt.getBounds()) && !hit) {
					hit = true;
					lives -= 1;
					if (lives > 0) {
						handler.clearLevel();

						Game.loadLevel();
					}
				}
			}

		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(texture, (int) x, (int) y, null);
		g.setColor(Color.red);
		if (lives <= 0) {
			String test = "Gameover!";
			String test2 = "  Continue?";
			g.drawChars(test.toCharArray(), 0, test.length(), 400, 300);
			g.drawChars(test2.toCharArray(), 0, test2.length(), 400, 320);
		} else {
			for (int i = 0; i < lives; i++) {
				g.fillRoundRect(10 + i * 20, 10, 10, 10, 10, 10);
			}
		}

	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) ((int) x + (width / 2 - (width / 2) / 2)), (int) ((int) y + (height / 2)),
				(int) width / 2, (int) height / 2);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle((int) ((int) x + (width / 2) - ((width / 2) / 2)), (int) y, (int) width / 2,
				(int) height / 2);
	}

	public Rectangle getBoundsRight() {
		return new Rectangle((int) ((int) x + width - 5), (int) y + 5, 5, (int) height - 10);
	}

	public Rectangle getBoundsLeft() {
		return new Rectangle((int) x, (int) y + 5, 5, (int) height - 10);
	}
}
