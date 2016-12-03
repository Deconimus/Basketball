package visionCore.math;

import java.io.Serializable;

public class Vec2f implements Serializable {

	
	private static final long serialVersionUID = 7930876875808633855L;
	
	
	public float x, y;
	
	
	public Vec2f() {
		
		this(0f, 0f);
	}
	
	public Vec2f(Vec2f vec) {
		
		this(vec.x, vec.y);
	}
	
	public Vec2f(float x, float y) {
		
		this.x = x; this.y = y;
	}
	
	
	public float getScalarProd(Vec2f vec) {
		
		return x * vec.x + y * vec.y;
	}
	
	
	public Vec2f add(Vec2f vec) {
		
		return add(vec.x, vec.y);
	}
	
	public Vec2f add(float scale) {
		
		return add(scale, scale);
	}
	
	public Vec2f add(float x, float y) {
		
		this.x += x;
		this.y += y;
		
		return this;
	}
	
	
	public Vec2f scale(Vec2f vec) {
		
		return scale(vec.x, vec.y);
	}
	
	public Vec2f scale(float scale) {
		
		return scale(scale, scale);
	}
	
	public Vec2f scale(float x, float y) {
		
		this.x *= x;
		this.y *= y;
		
		return this;
	}
	
	
	public void set(Vec2f vec) {
		
		set(vec.x, vec.y);
	}
	
	public void set(float x, float y) {
		
		this.x = x; this.y = y;
	}
	
	
	public float getLength() {
		
		return (float)Math.sqrt(x * x + y * y);
	}
	
}
