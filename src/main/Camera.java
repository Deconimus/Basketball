package main;

import org.lwjgl.opengl.Display;
import visionCore.math.Vec2f;

public class Camera {

	private Vec2f pos;
	private float levelWidth, levelHeight;
	
	public Camera(float levelWidth, float levelHeight) {
		
		this.levelWidth = levelWidth; this.levelHeight = levelHeight;
		
		pos = new Vec2f(0f, levelHeight - Display.getHeight());
		
	}
	
	public void update(float y) {
		
		pos.y = y - Display.getHeight() / 2f;
		
		if (pos.y < 0f) {
			pos.y = 0f;
		} else if (pos.y > levelHeight - Display.getHeight()) {
			pos.y = levelHeight - Display.getHeight();
		}
		
	}
	
	public void setPos(Vec2f pos) { this.pos = pos; }
	public Vec2f getPos() { return pos; }
	
}
