package main;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import visionCore.geom.Color;
import visionCore.math.Vec2f;

public class Lights {

	private Image img;
	private ArrayList<LightSpot> lights;
	
	public Lights(Map map) throws SlickException {
		
		img = new Image("textures/light.png");
		img.setFilter(Image.FILTER_LINEAR);
		lights = new ArrayList<LightSpot>();
		
		generate(map.map);
	}
	
	private void generate(int[][][] map) {
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				for (int k = 0; k < map[0][0].length; k++) {
					
					if (map[i][j][k] == 26) {
						
						lights.add(new LightSpot(new Vec2f(j * 64f + 32f - img.getWidth() / 3f, k * 64f - img.getHeight() * 0.5f), 2f));
						
					} else if (map[i][j][k] == 27) {
						
						lights.add(new LightSpot(new Vec2f(j * 64f + 32f - img.getWidth() + img.getWidth() / 3f, k * 64f - img.getHeight() * 0.5f), 2f));
					}
				}
			}
		}
	}
	
	public void render(Graphics g, float camY) {
		
		g.setDrawMode(Graphics.MODE_ADD);
		img.startUse();
		
		float displayHeight = Display.getHeight();
		
		for (int i = 0; i < lights.size(); i++) {
			
			float diffX, diffY;
			
			diffX = img.getWidth() * lights.get(i).scale - img.getWidth();
			diffY = img.getHeight() * lights.get(i).scale - img.getHeight();
			
			float x = lights.get(i).pos.x - diffX * 0.5f;
			float y = lights.get(i).pos.y - diffY * 0.5f - camY;
			
			if (y + img.getHeight() * lights.get(i).scale < 0f || y > displayHeight) { continue; }
			
			img.drawEmbedded(x, y, img.getWidth() * lights.get(i).scale, img.getHeight() * lights.get(i).scale);
		}
		
		img.endUse();
		g.setDrawMode(Graphics.MODE_NORMAL);
		
		/*
		g.setColor(Color.red);
		for (LightSpot light : lights) {
			
			float diffX = img.getWidth() * light.scale - img.getWidth();
			float diffY = img.getHeight() * light.scale - img.getHeight();
			
			float x = light.pos.x - diffX * 0.5f;
			float y = light.pos.y - diffY * 0.5f - camY;
			
			g.fillRect(x + img.getWidth() * light.scale * 0.5f - 2f, y + img.getHeight() * light.scale * 0.5f - 2f, 6f, 6f);
		}
		g.setColor(Color.white);
		*/
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
