package main;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import visionCore.geom.Vector2f;

public class Map {

	private int[][][] map;
	private SpriteSheet spriteSheet;
	
	public Map() throws SlickException {
		
		spriteSheet = new SpriteSheet(new Image("textures/spriteSheet.png"), 16, 16);
		map = new int[3][13][128];
		
		generate();
		
	}
	
	private void generate() {
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				for (int k = 0; k < map[0][0].length; k++) {
					
					map[i][j][k] = 0;
					
				}
			}
		}
		
		//Bg
		
		for (int i = 0; i < map[0].length; i++) {
			for (int j = 0; j < map[0][0].length; j++) {
				
				if (j < map[0][0].length - 4) {
					map[0][i][j] = 1;
				} else if (j < map[0][0].length - 3) {
					map[0][i][j] = 3;
				} else if (j < map[0][0].length) { 
					map[0][i][j] = 2;
				}
				
			}
		}
		
		//Walls
		
		for (int i = 0; i < map[0][0].length; i++) {
			map[2][0][i] = 4;
		}
		
		for (int i = 0; i < map[0][0].length; i++) {
			map[2][12][i] = 11;
		}
		
		//Floor
		
		map[2][0][map[0][0].length-1] = 5;
		map[2][12][map[0][0].length-1] = 15;
		
		for (int i = 1; i < map[0].length-1; i++) {
			map[2][i][map[0][0].length-1] = 6;
		}
		
		//Basket
		
		map[1][12][map[0][0].length-6] = 10;
		map[1][11][map[0][0].length-6] = 9;
		map[1][12][map[0][0].length-5] = 14;
		map[1][11][map[0][0].length-5] = 13;
		map[1][10][map[0][0].length-5] = 12;
		
		//Clock
		
		map[1][2][map[0][0].length-5] = 7;
		
		//Scoreboard
		
		map[1][10][map[0][0].length-8] = 19;
		map[1][11][map[0][0].length-8] = 23;
		
		//Seat
		
		map[1][1][127] = 28;
		map[1][2][127] = 29;
		map[1][3][127] = 29;
		map[1][4][127] = 29;
		map[1][5][127] = 29;
		map[1][6][127] = 30;
		
		//Window
		
		for (int i = 2; i < 10; i++) {
			if (i == 2) {
				map[0][i][116] = 16;
			} else if (i == 9) {
				map[0][i][116] = 18;
			} else { map[0][i][116] = 17; }
		}
		
		for (int i = 117; i < 121; i++) {
			for (int j = 2; j < 10; j++) {
				
				if (j == 2) {
					map[0][j][i] = 20;
				} else if (j == 9) {
					map[0][j][i] = 22;
				} else { map[0][j][i] = 0; }
				
			}
		}
		
		for (int i = 2; i < 10; i++) {
			if (i == 2) {
				map[0][i][121] = 24;
			} else if (i == 9) {
				map[0][i][121] = 25;
			} else { map[0][i][121] = 21; }
		}
		
		//lights
		
		map[1][0][121] = 26;
		map[1][12][121] = 27;
		map[1][0][108] = 26;
		map[1][12][108] = 27;
		
	}
	
	public void render(Graphics g, float camY, int layers, int layerStart) {
		
		int idX = 0; int idY = 0;
		
		for (int i = layerStart; i < layers; i++) {
			for (int j = 0; j < map[0].length; j++) {
				for (int k = 0; k < map[0][0].length; k++) {
					
					if (k * 64f - camY + 64f > 0f && k * 64f - camY - 64f < StartingClass.data.displayHeight && map[i][j][k] != 0) {
					
						idY = map[i][j][k] / 4;
						idX = map[i][j][k] - 4 * idY;
						
						spriteSheet.getSprite(idX, idY).drawEmbedded(64f * j, 64f * k - camY, 64f, 64f);
						
					}
					
				}
			}
		}
		
	}
	
	public void renderBack(Graphics g, float camY) {
		render(g, camY, 2, 0);
	}
	
	public void renderFor(Graphics g, float camY) {
		render(g, camY, 3, 2);
	}
	
	public float getLevelHeight() {
		return map[0][0].length * 64f;
	}
	
	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
	}
	
}
