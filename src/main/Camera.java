package main;

import org.lwjgl.opengl.Display;
import visionCore.geom.Vector2f;

public class Camera {

	private Vector2f pos;
	private float levelWidth, levelHeight;
	
	public Camera(float levelWidth, float levelHeight) {
		
		this.levelWidth = levelWidth; this.levelHeight = levelHeight;
		
		pos = new Vector2f(0f, levelHeight - StartingClass.data.displayHeight);
		
	}
	
	public void update(float y) {
		
		pos.y = y - StartingClass.data.displayHeight / 2f;
		
		if (pos.y < 0f) {
			pos.y = 0f;
		} else if (pos.y > levelHeight - StartingClass.data.displayHeight) {
			pos.y = levelHeight - StartingClass.data.displayHeight;
		}
		
	}
	
	public void setPos(Vector2f pos) { this.pos = pos; }
	public Vector2f getPos() { return pos; }
	
}
