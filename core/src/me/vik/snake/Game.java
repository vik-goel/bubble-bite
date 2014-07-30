package me.vik.snake;

import me.vik.snake.input.HeadInput;
import me.vik.snake.screen.GameScreen;

import com.badlogic.gdx.Screen;


public class Game extends com.badlogic.gdx.Game {

	public static final float GRID_SIZE = 0.05f;
	
	private Screen game;
	private HeadInput headInput;
	
	public Game(HeadInput headInput) {
		this.headInput = headInput;
	}
	
	public void create() {
		game = new GameScreen(this, headInput);
		setScreen(game);
	}
}
