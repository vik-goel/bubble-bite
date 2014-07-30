package me.vik.snake;

import me.vik.snake.input.HeadInput;
import me.vik.snake.screen.DifficultySelectionScreen;
import me.vik.snake.screen.GameOverScreen;
import me.vik.snake.screen.GameScreen;
import me.vik.snake.screen.HowToScreen;
import me.vik.snake.screen.MenuScreen;

import com.badlogic.gdx.Screen;


public class Game extends com.badlogic.gdx.Game {

	public static final float GRID_SIZE = 0.05f;
	
	private HeadInput headInput;
	
	private Screen menu, howTo, difficultySelection, game, gameOver;
	
	public Game(HeadInput headInput) {
		this.headInput = headInput;
	}
	
	public void create() {
		switchToMenuScreen();
	}
	
	public void switchToGameOverScreen() {
		if (gameOver == null)
			gameOver = new GameOverScreen(this);
		
		setScreen(gameOver);
	}
	
	public void switchToMenuScreen() {
		if (menu == null)
			menu = new MenuScreen(this);
		
		setScreen(menu);
	}
	
	public void switchToDifficultySelectionScreen() {
		if (difficultySelection == null)
			difficultySelection = new DifficultySelectionScreen(this);
		
		setScreen(difficultySelection);
	}
	
	public void switchToGameScreen() {
		if (game == null)
			game = new GameScreen(this, headInput);
		
		setScreen(game);
	}
	
	public void switchToHowToScreen() {
		if (howTo == null)
			howTo = new HowToScreen(this);
		
		setScreen(howTo);
	}
}
