package main;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;

import visionCore.geom.Color;
import visionCore.math.FastMath;
import visionCore.math.Vec2f;

public class BasketBall {

	private Vec2f pos, velocity;
	private float decceleration, airResistance, levelHeight, rotSpeed, rot, rotSpeedDec;
	private Image img;
	private Rectangle r, basketR, basketWallR;
	public boolean scoreChange, collidingBasket;
	
	public BasketBall(Vec2f pos, Image img, float levelHeight) {
		
		this.pos = pos;
		this.img = img;
		this.levelHeight = levelHeight;
		
		velocity = new Vec2f(0f, 0f);
		decceleration = 0.002f;
		airResistance = 0.0002f;
		
		r = new Rectangle(pos.x, pos.y, 64f, 64f);
		basketR = new Rectangle(11f * 64f - 16f, 123f * 64f + 8f, 64f + 12f, 56f);
		basketWallR = new Rectangle(12f * 64f - 4f, 122f * 64f + 4f, 16f, 128f - 16f);
		
		scoreChange = false; collidingBasket = false;
		
	}
	
	public void init() {
		
	}
	
	public void update(int delta) {
		
		move(delta);
	}
	
	public void render(Graphics g, float camY) {
		
		img.startUse();
		
			img.drawEmbedded(pos.x, pos.y - camY, 64f, 64f/*, rot*/);
		
		img.endUse();
	}
	
	private void move(int delta) {
		
		velocity.y += decceleration * delta;
		
		if (velocity.y - airResistance * (velocity.y * velocity.y) * delta > 0f) {
			velocity.y -= airResistance * (velocity.y * velocity.y) * delta;
		} else if (velocity.y + airResistance * (velocity.y * velocity.y) * delta < 0f) {
			velocity.y += airResistance * (velocity.y * velocity.y) * delta;
		}
		
		if (velocity.x - airResistance * (velocity.x * velocity.x) * delta > 0f) {
			velocity.x -= airResistance * (velocity.x * velocity.x) * delta;
		} else if (velocity.x + airResistance * (velocity.x * velocity.x) * delta < 0f) {
			velocity.x += airResistance * (velocity.x * velocity.x) * delta;
		}
		
		r.setX(pos.x);
		r.setY(pos.y);
		
		if (checkCollision()) {
			
			velocity.x *= 0.8f;
			velocity.y *= 0.8f;
			
			rotSpeed = velocity.x * 3;

			rotSpeedDec = 0f;
			
			r.setX(pos.x);
			r.setY(pos.y);
		}
		
		if ((velocity.x > 0f && velocity.x < 0.01f) || (velocity.x < 0f && velocity.x > -0.01f)) {
			
			velocity.x = 0f;
		}
		
		if (rotSpeed != 0f) {
			
			rotSpeedDec = 0.00001f;
			
			if (rotSpeed > 0f) {
				if (rotSpeed - rotSpeedDec * delta < 0f) {
					rotSpeed = 0f;
				} else { rotSpeed -= rotSpeedDec * delta; }
			} else if (rotSpeed < 0f) {
				if (rotSpeed + rotSpeedDec > 0f) {
					rotSpeed = 0f;
				} else { rotSpeed += rotSpeedDec * delta; }
			}
			
			rot += rotSpeed * delta;
		}
		
		rot = FastMath.normalizeCircular(rot, 0f, 360f);
		
		pos.x += velocity.x * delta;
		pos.y += velocity.y * delta;
		
		r.setX(pos.x);
		r.setY(pos.y);
	}
	
	private boolean checkCollision() {
		
		boolean collided = false;
		
		if (pos.x < 32f) {
			velocity.x = -velocity.x;
			pos.x = 33f;
			collided = true;
		} else if (pos.x > Display.getWidth() - 32f - 56f) {
			velocity.x = -velocity.x;
			pos.x = Display.getWidth() - 32f - 56f - 1f;
			collided = true;
		}
		
		if (pos.y <= 0f) {
			velocity.y = -velocity.y;
			collided = true;
		} else if (pos.y + 56f >= levelHeight - 16f) {
			pos.y = levelHeight - 16f - 56f - 1f;
			velocity.y = -velocity.y;
			if (velocity.y > -0.35f && velocity.y < 0f) {
				velocity.y = 0;
				pos.y = levelHeight - 16f - 56f;
			}
			collided = true;
		}
		
		if (r.intersects(basketWallR)) {
			
			if (pos.x + r.getWidth() > basketWallR.getX()) {
				
				velocity.x = -velocity.x;
				pos.x = basketWallR.getX() - 64f - 1f;
				collided = true;
			}
		}
		
		if (r.intersects(basketR)) {
			if (pos.x > basketR.getX() - 32f && pos.y + 64f > basketR.getY() + 2f && velocity.y > 0f && pos.y + 64f < basketR.getY() + 8f) {
				scoreChange = true;
				velocity.y *= 0.5f;
				velocity.x *= 0.5f;
				collidingBasket = true;
			}
		} else { collidingBasket = false; }
		
		return collided;
	}
	
	public void setPos(Vec2f pos) { this.pos.set(pos); this.r.setLocation(pos.x, pos.y); }
	public Vec2f getPos() { return pos; }
	
	public void setVelocity(Vec2f velocity) { this.velocity = velocity; }
	public void setVelocity(float x, float y) { velocity.set(x, y); }
	public Vec2f getVelocity() { return velocity; }
	
	public Rectangle getR() { return r; }
	
	public Rectangle getBasketR() { return basketR; }
	
	public Rectangle getBasketWallR() { return basketWallR; }
	
}
