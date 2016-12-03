package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class StartingClass extends BasicGame {

	public Level level;
	
	private static boolean started;
	
	public StartingClass() {
		super("Basketball by Deconimus");
	}
	
	public static void main(String args[]) throws SlickException {
		
		AppGameContainer game = new AppGameContainer(new StartingClass());
		
		started = false;
		
		game.setDisplayMode(832, 576, false);
		game.setTargetFrameRate(60);
		game.start();
		
	}
	
	@Override
	public void init(GameContainer gc) throws SlickException {
		
		started = true;
		level = new Level();
		
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if (started) {
			level.update(gc, delta);
		}
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (started) {
			level.render(gc, g);
		}
	}
	
}
