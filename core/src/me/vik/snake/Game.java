package me.vik.snake;

import java.util.Random;

import me.vik.snake.input.HeadInput;
import me.vik.snake.screen.DifficultySelectionScreen;
import me.vik.snake.screen.GameOverScreen;
import me.vik.snake.screen.GameScreen;
import me.vik.snake.screen.HowToScreen;
import me.vik.snake.screen.MenuScreen;
import me.vik.snake.util.Difficulty;
import me.vik.snake.util.ScoreManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;

public class Game extends com.badlogic.gdx.Game {

	public static final float GRID_SIZE = 0.05f;
	public static final String PREFERENCES_KEY = "prefs-bubble";

	private HeadInput headInput;

	private MenuScreen menu;
	private GameScreen game;
	private GameOverScreen gameOver;
	private Screen howTo, difficultySelection;
	private Music[] songs;
	private Random random = new Random();

	public Game(HeadInput headInput) {
		this.headInput = headInput;
	}

	public void create() {
		createBackgroundMusic(2);
		Gdx.graphics.setContinuousRendering(false);
		switchToMenuScreen();
	}

	private void createBackgroundMusic(int numSongs) {
		songs = new Music[numSongs];
		
		for (int i = 0; i < numSongs; i++)
			songs[i] = Gdx.audio.newMusic(Gdx.files.internal("sound/background " + (i + 1) + ".ogg"));

		for (int i = 0; i < numSongs; i++) {
			final int nextSong = i + 1 == numSongs ? 0 : i + 1;

			songs[i].setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(Music music) {
					if (menu.isSoundOn())
						songs[nextSong].play();
				}
			});
		}

		songs[random.nextInt(songs.length)].play();
	}

	public void switchToGameOverScreen(ScoreManager scoreManager) {
		if (gameOver == null)
			gameOver = new GameOverScreen(this);
		
		gameOver.setScoreManager(scoreManager);

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
		game.setDifficulty(difficulty);
	}

	public void switchToHowToScreen() {
		if (howTo == null)
			howTo = new HowToScreen(this);

		setScreen(howTo);
		Gdx.graphics.requestRendering();
	}

	public void setMusicEnabled(boolean enabled) {
		for (int i = 0; i < songs.length; i++)
			songs[i].stop();

		if (enabled)
			songs[random.nextInt(songs.length)].play();
	}
}
