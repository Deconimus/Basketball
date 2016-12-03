package main;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import visionCore.math.Vec2f;

public class Lights {

	private Image img;
	private ArrayList<LightSpot> lights;
	
	public Lights() throws SlickException {
		
		img = new Image("textures/light.png");
		img.setFilter(Image.FILTER_LINEAR);
		lights = new ArrayList<LightSpot>();
		
		generate();
		
	}
	
	private void generate() {
		
		lights.add(new LightSpot(new Vec2f(-img.getWidth() / 3f, 121 * 64f - img.getHeight() * 0.5f), 2f));
		lights.add(new LightSpot(new Vec2f(12 * 64f - img.getWidth() + img.getWidth() / 3f, 121 * 64f - img.getHeight() * 0.5f), 2f));
		
		lights.add(new LightSpot(new Vec2f(-img.getWidth() / 3f, 108 * 64f - img.getHeight() * 0.5f), 2f));
		lights.add(new LightSpot(new Vec2f(12 * 64f - img.getWidth() + img.getWidth() / 3f, 108 * 64f - img.getHeight() * 0.5f), 2f));
		
	}
	
	public void render(Graphics g, float camY) {
		
		g.setDrawMode(Graphics.MODE_ADD);
		img.startUse();
		
		for (int i = 0; i < lights.size(); i++) {
			
			float diffX, diffY;
			
			diffX = img.getWidth() * lights.get(i).scale - img.getWidth();
			diffY = img.getHeight() * lights.get(i).scale - img.getHeight();
			
			img.drawEmbedded(lights.get(i).pos.x - diffX * 0.5f, lights.get(i).pos.y - diffY * 0.5f - camY,
											img.getWidth() * lights.get(i).scale, img.getHeight() * lights.get(i).scale);
		}
		
		img.endUse();
		g.setDrawMode(Graphics.MODE_NORMAL);
		
	}
	
	private class LightSpot {
		
		public Vec2f pos;
		public float scale;
		
		public LightSpot(Vec2f pos, float scale) {
			
			this.pos = new Vec2f(pos);
			this.scale = scale;
			
		}
		
	}
	
}
