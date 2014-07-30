package me.vik.snake.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class Textures {

	public static Texture soundOn = new Texture("sound on.png");
	public static Texture soundOff = new Texture("sound off.png");
	
	public static Texture backButton = new Texture("back button.png");
	public static Texture restartButton = new Texture("restart button.png");
	public static Texture menuButton = new Texture("menu button.png");
	
	public static Texture easyDifficulty = new Texture("easy button.png");
	public static Texture mediumDifficulty = new Texture("medium button.png");
	public static Texture hardDifficulty = new Texture("hard button.png");
	
	public static Texture playOff = new Texture("play on.png");
	public static Texture howToOff = new Texture("how to on.png");
	
	public static Texture gameOverRibbon = new Texture("game over ribbon.png");
	public static Texture background = new Texture("background.png");
	
	public static Texture[] head = new Texture[4];
	public static Texture link = new Texture("link.png");
	public static Texture food = new Texture("food.png");
	public static Texture particle = new Texture("particle.png");
	public static Texture edge = new Texture("edge.png");
	
	static {
		gameOverRibbon.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		background.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		head[0] = new Texture("head up.png");
		head[1] = new Texture("head right.png");
		head[2] = new Texture("head down.png");
		head[3] = new Texture("head left.png");
	}
	
}
