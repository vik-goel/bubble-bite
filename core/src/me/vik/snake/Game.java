package me.vik.snake;

import me.vik.snake.input.HeadInput;
import me.vik.snake.input.KeyboardHeadInput;
import me.vik.snake.input.TouchHeadInput;
import me.vik.snake.screen.CreditsScreen;
import me.vik.snake.screen.DifficultySelectionScreen;
import me.vik.snake.screen.GameOverScreen;
import me.vik.snake.screen.GameScreen;
import me.vik.snake.screen.HowToScreen;
import me.vik.snake.screen.MenuScreen;
import me.vik.snake.screen.RenderScreen;
import me.vik.snake.util.Difficulty;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

public class Game extends com.badlogic.gdx.Game {

	public static final float GRID_SIZE = 1f / 17f;
	public static final String PREFERENCES_KEY = "prefs-bubble";

	private HeadInput headInput;

	private MenuScreen menu;
	private GameScreen game;
	private GameOverScreen gameOver;
	private RenderScreen howTo, difficultySelection, credits;

	private int frameCount = 0;

	public void create() {
		if (Gdx.app.getType() == ApplicationType.Android || Gdx.app.getType() == ApplicationType.iOS)
			headInput = new TouchHeadInput(this);
		else headInput = new KeyboardHeadInput();
		
		Gdx.graphics.setContinuousRendering(false);
		switchToMenuScreen();
	}

	public void render() {
		super.render();

		if (++frameCount == 2)
			GameScreen.createMusic();
	}

	public void switchToGameOverScreen(Difficulty difficulty) {
		if (gameOver == null)
			gameOver = new GameOverScreen(this);

		gameOver.setScoreManager(difficulty);

		setScreen(gameOver);
		Gdx.graphics.requestRendering();
	}

	public void switchToMenuScreen() {
		if (menu == null)
			menu = new MenuScreen(this);

		setScreen(menu);
		Gdx.graphics.requestRendering();
	}

	public void switchToDifficultySelectionScreen() {
		if (difficultySelection == null)
			difficultySelection = new DifficultySelectionScreen(this);

		setScreen(difficultySelection);
		Gdx.graphics.requestRendering();
	}

	public void switchToGameScreen(Difficulty difficulty) {
		if (game == null)
			game = new GameScreen(this, headInput);

		setScreen(game);
		game.init(difficulty, menu.isSoundOn());
	}

	public void switchToHowToScreen() {
		if (howTo == null)
			howTo = new HowToScreen(this);

		setScreen(howTo);
		Gdx.graphics.requestRendering();
	}

	public void switchToCreditsScreen() {
		if (credits == null)
			credits = new CreditsScreen(this);

		setScreen(credits);
		Gdx.graphics.requestRendering();
	}

}
