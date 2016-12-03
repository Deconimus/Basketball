package main;

import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.opengl.shader.ShaderProgram;
import org.newdawn.slick.util.ResourceLoader;

import visionCore.geom.Color;
import visionCore.math.Vec2f;

public class Level {

	private Image bg;
	
	public float levelWidth, levelHeight;
	
	private Camera camera;
	private BasketBall ball;
	private Map map;
	
	private Vec2f mousePos;
	private boolean makeLine;
	private Rectangle mouseR;
	
	private Vec2f linePointA, linePointB;
	
	ShaderProgram darkenShader;
	
	private Lights lights;
	
	private int score;
	
	private TrueTypeFont font;
	
	public Level() throws SlickException {
		
		map = new Map();
		
		levelWidth = Display.getWidth();
		levelHeight = map.getLevelHeight();
		
		ball = new BasketBall(new Vec2f(Display.getWidth() / 2f, levelHeight - Display.getHeight() * 0.5f), map.getSpriteSheet().getSprite(0, 0), levelHeight);
		camera = new Camera(levelWidth, levelHeight);
		
		mousePos = new Vec2f();
		mouseR = new Rectangle(mousePos.x, mousePos.y, 24f, 24f);
		makeLine = false;
		
		linePointA = new Vec2f(); linePointB = new Vec2f();
		
		bg = new Image("textures/bg.png");
		
		darkenShader = ShaderProgram.loadProgram("textures/darken_shader.vert", "textures/darken_shader.frag");
		
		lights = new Lights();
		
		try {
			InputStream fontIS = ResourceLoader.getResourceAsStream("pixel-font.ttf");
			java.awt.Font fnt = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, fontIS);
			fnt = fnt.deriveFont(40f);
			font = new TrueTypeFont(fnt, false);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() throws SlickException {
		
		
		
	}
	
	public void update(GameContainer gc, int delta) throws SlickException {
		
		if (!makeLine) {
			ball.update(delta);
			if (ball.scoreChange && !ball.collidingBasket) { score++; ball.scoreChange = false; }
			camera.update(ball.getPos().y);
		}
		handleInput(gc);
		
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {
	
		bg.draw();
		
		darkenShader.bind();
		darkenShader.setUniform1f("uSubs", 0.55f);
		
		map.getSpriteSheet().startUse();
		
		map.renderBack(g, camera.getPos().y);
		ball.render(g, camera.getPos().y);
		
		map.getSpriteSheet().endUse();
		
		darkenShader.unbind();
		
		renderScore(g);
		lights.render(g, camera.getPos().y);
		
		darkenShader.bind();
		darkenShader.setUniform1f("uSubs", 0.55f);
		map.getSpriteSheet().startUse();
		map.renderFor(g, camera.getPos().y);
		map.getSpriteSheet().endUse();
		darkenShader.unbind();
		
		if (makeLine) {
			
			g.setColor(Color.black);
			g.drawLine(linePointA.x, linePointA.y, mousePos.x, mousePos.y);
			g.setColor(Color.white);
		}
		
	}
	
	private void renderScore(Graphics g) {
		
		String scoreString = (score / 10)+""+(score - (int)(score / 10) * 10);
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString(scoreString, 10f * 64f + (64f - font.getWidth(scoreString) / 2f), 120f * 64f + (64f - font.getHeight()) * 0.5f + 2f - camera.getPos().y);
		
	}
	
	private void handleInput(GameContainer gc) throws SlickException {
		
		Input input = gc.getInput();
		
		mousePos.x = input.getMouseX();
		mousePos.y = input.getMouseY();
		
		mouseR.setLocation(mousePos.x, mousePos.y);
		
		Rectangle ballR = ball.getR();
		ballR.setLocation(ball.getR().getX(), ball.getR().getY() - camera.getPos().y);
		
		if (mouseR.intersects(ballR)) {
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				linePointA = new Vec2f(ball.getPos().x + 32f, ball.getPos().y + 32f - camera.getPos().y);
				makeLine = true;
			}
		}
		
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) == false && makeLine) {
			linePointB = new Vec2f(mousePos);
			pushBasketBall();
			makeLine = false;
		}
		
	}
	
	private void pushBasketBall() {
		
		Vec2f diff = new Vec2f(0f, 0f);
		
		diff.x = linePointB.x - linePointA.x;
		diff.y = linePointB.y - linePointA.y;
		
		diff.x *= 0.009f;
		diff.y *= 0.009;
		
		ball.setVelocity(diff);
		
	}
	
}
