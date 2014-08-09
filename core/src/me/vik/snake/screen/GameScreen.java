package me.vik.snake.screen;

import java.util.ArrayList;
import java.util.Random;

import me.vik.snake.Game;
import me.vik.snake.gameobject.EdgeObject;
import me.vik.snake.gameobject.Food;
import me.vik.snake.gameobject.GameObject;
import me.vik.snake.gameobject.Head;
import me.vik.snake.gameobject.Link;
import me.vik.snake.gameobject.ParticlePool;
import me.vik.snake.input.HeadInput;
import me.vik.snake.input.TouchInput;
import me.vik.snake.util.CameraShaker;
import me.vik.snake.util.Difficulty;
import me.vik.snake.util.Fonts;
import me.vik.snake.util.RenderUtil;
import me.vik.snake.util.ScoreManager;
import me.vik.snake.util.Textures;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Music.OnCompletionListener;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen extends RenderScreen {

	private static final int NUM_FOOD = 3;

	private static Random random = new Random();
	private static Music[] songs;
	private static boolean soundOn = false;

	private HeadInput headInput;

	private ArrayList<GameObject> gameObjects;
	private ParticlePool particlePool;
	private Head head;

	private SpriteBatch fontBatch = new SpriteBatch();

	private Difficulty difficulty;

	private int maxX, maxY;
	private boolean destroyed;

	private Rectangle pauseButtonBounds = new Rectangle(getAspectRatio() - Game.GRID_SIZE - getXOffset(), 1 - Game.GRID_SIZE, Game.GRID_SIZE, Game.GRID_SIZE);
	private PauseMenu pauseMenu;
	private boolean paused = false;

	private String scoreOutput;
	private int pastScore;

	public GameScreen(Game game, HeadInput headInput) {
		super(game);

		this.headInput = headInput;

		pauseMenu = new PauseMenu(game);
		initGameObjects();

		particlePool = new ParticlePool(500, camera);
	}

	private void initGameObjects() {
		gameObjects = new ArrayList<GameObject>(100);

		maxX = (int) (getAspectRatio() / Game.GRID_SIZE) - 1;
		maxY = (int) (1f / Game.GRID_SIZE) - 2;

		for (int i = 0; i < NUM_FOOD; i++)
			gameObjects.add(new Food(1, 1, maxX, maxY));

		for (int i = 0; i <= maxX; i++) {
			gameObjects.add(new EdgeObject(i, 0));
			gameObjects.add(new EdgeObject(i, maxY));
		}

		for (int i = 0; i <= maxY; i++) {
			gameObjects.add(new EdgeObject(0, i));
			gameObjects.add(new EdgeObject(maxX, i));
		}
	}

	public void updateScreen(float dt) {
		updatePauseMenu(dt);

		if (paused)
			return;

		updateCameraShaker(dt);
		particlePool.update(dt);

		if (!destroyed) {
			if (head.hasCollidedWithSelf()) {
				destroyed = true;
				head.destroy();
			}

			for (int i = 0; i < gameObjects.size(); i++)
				gameObjects.get(i).update(dt);
		} else {
			if (particlePool.getNumUsed() <= 12) {
				ScoreManager scoreManager = ScoreManager.getInstance(difficulty);
				scoreManager.setCurrentScore(head.getScore());
				scoreManager.writeBestScore();
				game.switchToGameOverScreen(difficulty);
			}
		}
	}

	private void updatePauseMenu(float dt) {
		if (!paused && !destroyed && TouchInput.isDown() && Gdx.input.justTouched() && pauseButtonBounds.contains(TouchInput.getX(), TouchInput.getY())) {
			paused = !paused;
			pauseMenu.setEnabled(paused, false);
		}

		pauseMenu.update(dt);

		if (!pauseMenu.isEnabled())
			paused = false;
	}

	private void updateCameraShaker(float dt) {
		CameraShaker.getInstance().setCamera(camera);
		CameraShaker.getInstance().update(dt);
	}

	public void renderScreen() {
		RenderUtil.renderGrid(camera, 1, 1, maxX, maxY, getXOffset());

		for (int i = 0; i < gameObjects.size(); i++)
			if (!destroyed || !(gameObjects.get(i) instanceof Head))
				gameObjects.get(i).render(batch, getXOffset());

		particlePool.render(batch, getXOffset());

		renderScore();
		renderPauseButton();

		pauseMenu.render(batch, getAspectRatio());
	}

	private void renderScore() {
		if (head.getScore() != pastScore) {
			pastScore = head.getScore();
			scoreOutput = "Score: " + pastScore;
		}

		final float fontHeight = 32f;
		float height = Gdx.graphics.getHeight() * Game.GRID_SIZE;

		float scale = height / fontHeight;
		Fonts.scoreFont.setScale(scale);

		TextBounds textBounds = Fonts.scoreFont.getBounds(scoreOutput);

		fontBatch.begin();
		Fonts.scoreFont.setColor(1, 1, 1, 1);
		Fonts.scoreFont.draw(fontBatch, scoreOutput, 5f + getXOffset() * Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - textBounds.height / 2 + 1f);
		fontBatch.end();
	}

	private void renderPauseButton() {
		batch.begin();
		batch.setColor(1, 1, 1, 1);
		batch.draw(Textures.pauseButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
		batch.end();
	}

	public void show() {
		Gdx.graphics.setContinuousRendering(true);

		particlePool.reset();

		if (head != null)
			gameObjects.remove(head);

		head = new Head(1, 1, maxX, maxY, headInput, particlePool);
		gameObjects.add(head);

		Food.reset(head);
		Link.resetRemovedLinks();

		destroyed = false;
		pastScore = -1;
	}

	public void hide() {
		Gdx.graphics.setContinuousRendering(false);

		for (int i = 0; i < songs.length; i++)
			songs[i].stop();
	}

	public void init(Difficulty difficulty, boolean soundOn) {
		head.setDifficulty(difficulty);
		head.setMusicEnabled(soundOn);

		this.difficulty = difficulty;
		pauseMenu.setDifficulty(difficulty);

		if (soundOn)
			songs[random.nextInt(songs.length)].play();

		for (int i = 0; i < gameObjects.size(); i++)
			if (gameObjects.get(i) instanceof Food)
				((Food) gameObjects.get(i)).setMusicEnabled(soundOn);
	}

	public float getXOffset() {
		return (getAspectRatio() % Game.GRID_SIZE) / 2;
	}

	public void pause() {
		if (!destroyed) {
			paused = true;
			pauseMenu.setEnabled(paused, true);
		}
	}

	public void resume() {

	}

	public void dispose() {

	}

	public static void createMusic() {
		final int numSongs = 2;

		songs = new Music[numSongs];

		for (int i = 0; i < numSongs; i++)
			songs[i] = Gdx.audio.newMusic(Gdx.files.internal("sound/background " + (i + 1) + ".ogg"));

		for (int i = 0; i < numSongs; i++) {
			final int nextSong = (i + 1 == numSongs ? 0 : i + 1);

			songs[i].setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(Music music) {
					if (soundOn)
						songs[nextSong].play();
				}
			});
		}

		Head.createMusic();
		Food.createMusic();
	}

	public void onBackPressed() {
		if (!destroyed && !paused) {
			paused = true;
			pauseMenu.setEnabled(paused, false);
		}	
	}

}
