/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package No.Name.Game.v3;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import objects.Block;
import objects.Enemy;
import objects.Finish;
import objects.ObjektId;
import objects.Player;
import texture.BufferedImageLoader;
import texture.Texture;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean running = false;
	private Thread thread;

	public static BufferedImage level = null;

	// objekter
	static Handler handler;
	static Texture tex;

	Random rnd = new Random();

	public static int levelnum = 1;
	public static boolean start = false;

	private void init() {
		tex = new Texture();

		BufferedImageLoader loader = new BufferedImageLoader();

		level = loader.loadImage("/levels/menu.png"); // lloader billede i
														// mappen

		handler = new Handler();
		// handler.addobjekt(new Player(100, 100,handler, ObjektId.Player));
		// handler.createLevel(); ////////////////////////////// laver et level
		// med hardcoadet bloke som map
		this.addKeyListener(new KeyInput(handler));
	}

	public synchronized void start() {
		if (running) // failsafe
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int ticks = 0;
		int frames = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				System.out.println(ticks + " ticks, " + frames + " frames");

				frames = 0;
				ticks = 0;
			}
		}
	}

	private void tick() {
		handler.tick();
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy(); // thsi refarere til
														// canvas, og buffer
														// bliver atomatisk null
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		///////////////////////////////////////
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (!start)
			g.drawImage(level, 5, 5, null);
		handler.render(g);

		//////////////////////////////////////
		g.dispose();
		bs.show();

	}

	private static void LoadImageLevel(BufferedImage image) {
		int b = image.getWidth();
		int h = image.getHeight();

		for (int xx = 0; xx < h; xx++) {
			for (int yy = 0; yy < b; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				if (red == 255 && green == 255 & blue == 255)
					handler.addobjekt(new Block(xx * 32, yy * 32, 1, ObjektId.BLOCK));
				if (red == 100 && green == 100 & blue == 100)
					handler.addobjekt(new Block(xx * 32, yy * 32, 0, ObjektId.BLOCK));
				if (red == 0 && green == 0 & blue == 255)
					handler.addobjekt(new Player(xx * 32, yy * 32, handler, ObjektId.PLAYER));
				if (red == 255 && green == 216 & blue == 0)
					handler.addobjekt(new Finish(xx * 32, yy * 32, ObjektId.GOAL));
				if (red == 200 && green == 200 & blue == 200)
					handler.addobjekt(new Enemy(xx * 32, yy * 32, ObjektId.ENEMY));
			}
		}
	}

	public static Texture getInstance() { // for vi kan få tex for all
		return tex;
	}

	public static void loadLevel() {
		BufferedImageLoader loader = new BufferedImageLoader();
		System.out.println("/levels/level" + levelnum + ".png");
		try {
			level = loader.loadImage("/levels/level" + levelnum + ".png"); // lloader
																			// billede
																			// i
																			// mappen
			LoadImageLevel(level);
		} catch (IllegalArgumentException e) {
			start = false;
			level = loader.loadImage("/levels/gz.png");
			System.err.println("No such Level!");
			handler.clearLevel();
		}

	}

	public static void main(String arfs[]) {
		new Window(800, 600, "No.Name.Game.v3", new Game());
	}
}
