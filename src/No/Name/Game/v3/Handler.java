/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package No.Name.Game.v3;

import java.awt.Graphics;
import java.util.LinkedList;

import objects.Objekt;

/**
 *
 * @author Janus
 */
public class Handler {

	public LinkedList<Objekt> objekt = new LinkedList<>();

	private Objekt tempObjekt;

	public void tick() {
		for (int i = 0; i < objekt.size(); i++) {
			tempObjekt = objekt.get(i);
			tempObjekt.tick(objekt);
		}
	}

	public void render(Graphics g) {
		for (int i = 0; i < objekt.size(); i++) {
			tempObjekt = objekt.get(i);
			tempObjekt.render(g);
		}
	}

	public void clearLevel() {
		objekt.clear();
	}

	public void addobjekt(Objekt objekt) {
		this.objekt.add(objekt);
	}

	public void removeobjekt(Objekt objekt) {
		this.objekt.remove(objekt);
	}
}