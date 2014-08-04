package me.vik.snake.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;

public class Textures {

	public static Texture soundOn = createTexture("sound on.png");
	public static Texture soundOff = createTexture("sound off.png");
	
	public static Texture backButton = createTexture("back button.png");
	public static Texture restartButton = createTexture("restart button.png");
	public static Texture menuButton = createTexture("menu button.png");
	public static Texture pauseButton = createTexture("pause button.png");
	public static Texture pauseMenu = createTexture("pause menu.png");
	public static Texture title = createTexture("title.png");
	
	public static Texture easyDifficulty = createTexture("easy button.png");
	public static Texture mediumDifficulty = createTexture("medium button.png");
	public static Texture hardDifficulty = createTexture("hard button.png");
	
	public static Texture playOff = createTexture("play on.png");
	public static Texture howToOff = createTexture("how to on.png");
	
	public static Texture gameOverRibbon = createTexture("game over ribbon.png");
	public static Texture background = createTexture("background.png");
	
	public static Texture head = createTexture("head up.png");
	public static Texture link = createTexture("link with gradient.png");
	public static Texture food = createTexture("food.png");
	public static Texture particle = createTexture("particle.png");
	public static Texture edge = createTexture("edge.png");

	private static Texture createTexture(String fileName) {
		Texture texture = new Texture(fileName);
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return texture;
	}
	
}
